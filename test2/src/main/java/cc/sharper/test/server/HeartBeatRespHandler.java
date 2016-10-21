package cc.sharper.test.server;

import cc.sharper.test.common.MessageType;
import cc.sharper.test.struct.Header;
import cc.sharper.test.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by liumin3 on 2016/10/21.
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("接收到客户端的心跳请求 : ---> " + message);
            NettyMessage heartBeat = buildHeatBeat();
            System.out.println("发送心跳响应去客户端 : ---> " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else ctx.fireChannelRead(msg);
    }

    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }

}

