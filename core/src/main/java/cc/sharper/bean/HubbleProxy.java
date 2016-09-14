package cc.sharper.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 动态代理的实际处理场所
 * Created by liumin3 on 2016/9/14.
 */
public class HubbleProxy
{
    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public HubbleProxy(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    public HubbleProxy(ServiceDiscovery serviceDiscovery)
    {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass)
    {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler()
                {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                    {
                        HubbleRequest request = new HubbleRequest(); // 创建并初始化 RPC 请求
                        request.setRequestId(UUID.randomUUID().toString());//
                        request.setClassName(method.getDeclaringClass().getName());// 类名
                        request.setMethodName(method.getName());//方法名
                        request.setParameterTypes(method.getParameterTypes());// 类型
                        request.setParameters(args);//参数

                        if (serviceDiscovery != null)
                        {
                            //serverAddress = serviceDiscovery.discover(); // 发现服务
                        }

                        String[] array = serverAddress.split(":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);

                        HubbleClient client = new HubbleClient(host, port); // 初始化 RPC 客户端 其实可以在本地留存一份信息
                        HubbleResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应

                        if (response.getError() != null)//发现异常
                        {
                            throw response.getError();
                        } else
                        {
                            return response.getResult();
                        }
                    }
                }
        );
    }

}
