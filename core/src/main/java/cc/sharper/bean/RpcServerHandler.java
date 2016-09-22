package cc.sharper.bean;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Created by liumin3 on 2016/9/21.
 */
public class RpcServerHandler  extends SimpleChannelInboundHandler<HubbleRequest>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private transient ApplicationContext applicationContext;

    public RpcServerHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //接受请求来的消息
    public void channelRead0(final ChannelHandlerContext ctx, HubbleRequest request) throws Exception {
        System.out.println("业务");
        HubbleResponse response = new HubbleResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);//这里的作用是 TODO
    }

    private Object handle(HubbleRequest request) throws Throwable
    {
        String className = request.getClassName();
        Object serviceBean = applicationContext.getBean(ContainProvider.allProvider.get(className));
//        Object serviceBean = tem.getRealRef();
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // Method method = serviceClass.getMethod(methodName, parameterTypes);
        // method.setAccessible(true);
        // return method.invoke(serviceBean, parameters);

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }
}
