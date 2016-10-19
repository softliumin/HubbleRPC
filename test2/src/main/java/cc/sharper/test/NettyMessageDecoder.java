package cc.sharper.test;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by liumin3 on 2016/10/19.
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }
}
