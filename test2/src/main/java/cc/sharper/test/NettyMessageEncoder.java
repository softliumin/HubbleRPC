package cc.sharper.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by liumin3 on 2016/10/19.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage>
{


    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {

    }
}
