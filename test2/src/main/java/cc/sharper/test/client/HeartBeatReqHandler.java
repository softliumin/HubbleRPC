package cc.sharper.test.client;

import cc.sharper.test.common.MessageType;
import cc.sharper.test.struct.Header;
import cc.sharper.test.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by liumin3 on 2016/10/21.
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter
{

    private volatile ScheduledFuture<?> heartBeat;

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        NettyMessage message = (NettyMessage) msg;
        // 握手成功，主动发送心跳消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value())
        {
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
        } else if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value())
        {
            System.out.println("客户端收到服务端的心跳信息 : ---> " + message);
        } else ctx.fireChannelRead(msg);
    }

    private class HeartBeatTask implements Runnable
    {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext ctx)
        {
            this.ctx = ctx;
        }

        @Override
        public void run()
        {
            NettyMessage heatBeat = buildHeatBeat();
            System.out.println("客户端发送心跳信息去服务端 : ---> " + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private NettyMessage buildHeatBeat()
        {
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        if (heartBeat != null)
        {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
