package cc.sharper.bean;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 发送消息的编码
 * Created by liumin3 on 2016/9/14.
 */
public class HubbleEncoder  extends MessageToByteEncoder
{
    private Class<?> genericClass;

    public HubbleEncoder(Class<?> genericClass)
    {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception
    {
        if (genericClass.isInstance(in))
        {
            byte[] data = HubbleSerializationUtil.serialize(in);
            out.writeInt(110); // 测试请求头的解析
            out.writeInt(data.length);
            out.writeBytes(data);
        }else
        {
            //异常
        }
    }
}
