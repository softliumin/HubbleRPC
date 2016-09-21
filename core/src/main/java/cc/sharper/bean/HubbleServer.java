package cc.sharper.bean;

import cc.sharper.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * hubble server配置
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleServer implements ApplicationContextAware,InitializingBean
{
    private static final Logger log = LoggerFactory.getLogger(HubbleServer.class);

    private Map<String, Object> handlerMap = new HashMap<String, Object>(); // 存放接口名与服务对象之间的映射关系

    private transient ApplicationContext applicationContext;

    /**
     *
     * server 的ID
     */
    private String id;

    /**
     *
     * 协议
     */
    private String protocol;

    /**
     *
     * 端口地址 默认是8080
     */
    private String port = "8080";

    public void setApplicationContext(ApplicationContext ctx) throws BeansException
    {
        this.applicationContext = ctx;
        // 获取所有带有 RpcService 注解的 Spring Bean
//        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
//        if (MapUtils.isNotEmpty(serviceBeanMap))
//        {
//            for (Object serviceBean : serviceBeanMap.values())
//            {
//                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
//                handlerMap.put(interfaceName, serviceBean);
//            }
//        }
    }

    public void afterPropertiesSet() throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception
                        {
                            channel.pipeline()
                                    .addLast(new HubbleDecoder(HubbleResponse.class))
                                    .addLast(new HubbleEncoder(HubbleRequest.class))
                                    .addLast(new RpcServerHandler(applicationContext));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String host = "127.0.0.1";//ip地址   NetUtils.getLocalHost()
            int port = Integer.parseInt("8080");//端口 8080

            ChannelFuture future = bootstrap.bind(host, port).sync();

            log.info("server启动了 {}", port);

            //暂时注释掉
//            if (serviceRegistry != null)
//            {
//                serviceRegistry.register(serverAddress); // 注册服务地址
//            }

            //future.channel().closeFuture().sync();
        }catch (Exception e)
        {
            log.error("监听端口异常",e);
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }





    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }
}
