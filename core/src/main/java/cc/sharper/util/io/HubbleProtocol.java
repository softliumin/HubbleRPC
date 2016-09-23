package cc.sharper.util.io;

import io.netty.buffer.ByteBuf;

/**
 * Hubble的传输的协议栈
 *
 *
 *
 *
 *
 *  Hubble的染色点
 * //=============================head begin ==================================================
 *  长度 包含head和body的长度
 *  时间
 *  协议栈
 *  序列化方式
 *  hubble版本
 *
 *
 *
 * //=============================head end ==================================================
 * //=============================body begin ==================================================
 *
 *
 * //=============================body end ==================================================
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * Created by liumin3 on 2016/9/23.
 */
public class HubbleProtocol implements  Protocol
{




    @Override
    public Object decode(ByteBuf datas, Class clazz)
    {
        return null;
    }

    @Override
    public Object decode(ByteBuf datas, String classTypeName)
    {
        return null;
    }

    @Override
    public ByteBuf encode(Object obj, ByteBuf buffer)
    {
        return null;
    }
}
