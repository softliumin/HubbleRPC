package cc.sharper.test.server;

import cc.sharper.test.common.MessageType;
import cc.sharper.test.struct.Header;
import cc.sharper.test.struct.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liumin3 on 2016/10/20.
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter
{

    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whitekList = {"127.0.0.1", "192.168.1.104"};


    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手请求消息，处理，其它消息透传
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value())
        {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;

            if (nodeCheck.containsKey(nodeIndex))
            {
                // 重复登陆，拒绝
                loginResp = buildResponse((byte) -1);
            } else
            {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for (String WIP : whitekList)
                {
                    if (WIP.equals(ip))
                    {
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK ? buildResponse((byte) 0) : buildResponse((byte) -1);
                if (isOK)
                {
                    nodeCheck.put(nodeIndex, true);
                }
            }
            System.out.println("登录返回是 : " + loginResp + " 消息体是 [" + loginResp.getBody() + "]");
            ctx.writeAndFlush(loginResp);//返回
        } else
        {
            ctx.fireChannelRead(msg);//进入下一步
        }
    }

    private NettyMessage buildResponse(byte result)
    {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        // 删除异常的节点注册信息
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存


        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
