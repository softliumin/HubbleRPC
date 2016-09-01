package cc.sharper.util.io;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  使用Protostuff 进行序列化
 * Created by liumin3 on 2016/9/1.
 */
public class ProtostuffUtil
{
    private  static Map<Class<?> ,Schema<?>> cacheScheme = new ConcurrentHashMap<Class<?> ,Schema<?>>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    private ProtostuffUtil() {
    }


    @SuppressWarnings("unchecked")
    public static <T> Schema<T> getChema(Class<T> cls)
    {
        Schema<T> schema =(Schema<T>) cacheScheme.get(cls);
        if (schema==null)
        {
            schema = RuntimeSchema.createFrom(cls);//
            if(schema!=null)
            {
                cacheScheme.put(cls, schema);//放到schema缓存map中
            }
        }
        return schema;

    }


    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj)
    {
        Class<T> cls =(Class<T>) obj.getClass();
        LinkedBuffer  buffer= LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);//声明空间

        try
        {
            Schema<T> schema = getChema(cls);
            return ProtostuffIOUtil.toByteArray(obj,schema,buffer);
        }catch (Exception e)
        {
            throw new IllegalStateException(e.getMessage(),e);
        }finally
        {
            buffer.clear();
        }

    }

    /**
     * 反序列化
     * @param data
     * @param cls
     * @param <M>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <M> M deserialize(byte[] data,Class<M> cls)
    {
        try
        {
            M message = (M) objenesis.newInstance(cls);
            Schema<M> schema = getChema(cls);
            ProtostuffIOUtil.mergeFrom(data,message,schema);
            return message;
        }catch (Exception e)
        {
            throw new IllegalStateException(e.getMessage(),e);
        }
    }
}
