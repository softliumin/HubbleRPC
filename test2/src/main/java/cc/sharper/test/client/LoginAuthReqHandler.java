package cc.sharper.test.client;

import cc.sharper.test.common.MessageType;
import cc.sharper.test.struct.Header;
import cc.sharper.test.struct.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import io.netty.channel.ChannelHandlerAdapter;

/**
 * Created by liumin3 on 2016/10/20.
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter
{


    //通道激活的时候
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("客户端发送握手请求--------");
        ctx.writeAndFlush(buildLoginReq());
    }


    // 接到消息的返回
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_RESP
                .value())
        {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0)
            {
                // 握手失败，关闭链路
                ctx.close();
            } else
            {
                System.out.println("登录ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else
        {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq()
    {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    //有异常了
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {
        ctx.fireExceptionCaught(cause);
    }
}
