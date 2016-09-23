package cc.sharper.util.io;

import io.netty.buffer.ByteBuf;

/**
 * Created by liumin3 on 2016/9/23.
 */
public interface Protocol
{

    Object decode(ByteBuf datas, Class clazz);


    Object decode(ByteBuf datas,String classTypeName);

    ByteBuf encode(Object obj, ByteBuf buffer);
}
