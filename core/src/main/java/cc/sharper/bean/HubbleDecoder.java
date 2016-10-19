package cc.sharper.bean;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码
 * Created by liumin3 on 2016/9/14.
 */
public class HubbleDecoder  extends ByteToMessageDecoder
{
    private Class<?> genericClass;

    public HubbleDecoder(Class<?> genericClass)
    {
        this.genericClass = genericClass;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if (in.readableBytes() < 4)
        {
            return;
        }
        in.markReaderIndex();
        int tt = in.readInt();
        System.out.println("ttttttttttttttttttttttttttt:"+tt);


        int dataLength = in.readInt();
        if (dataLength < 0)
        {
            ctx.close();
        }
        if (in.readableBytes() < dataLength)
        {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];//请求来的字节数组
        in.readBytes(data);

        Object obj = HubbleSerializationUtil.deserialize(data, genericClass);
        out.add(obj);
    }
}
