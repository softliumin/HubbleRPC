package cc.sharper.bean;

import cc.sharper.util.io.HubbleMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * hubble server配置
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleServer implements ApplicationContextAware,InitializingBean
{
    private static final Logger log = LoggerFactory.getLogger(HubbleServer.class);
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
                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,-4,4))
                                    .addLast(new HubbleDecoder(HubbleMessage.class))//HubbleRequest
                                    .addLast(new HubbleEncoder(HubbleMessage.class))//HubbleResponse
                                    .addLast(new RpcServerHandler(applicationContext));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String host = "127.0.0.1";//ip地址   NetUtils.getLocalHost()
            int port = Integer.parseInt("8080");//端口 8080

            //绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();//.sync()

            ChannelFuture channelFuture = future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess())
                    {
                        log.info("ok`````");
                    } else
                    {
                        log.info("end``````");
                        workerGroup.shutdownGracefully();
                        bossGroup.shutdownGracefully();
                    }

                }
            });

            try {
                channelFuture.await(5000, TimeUnit.MILLISECONDS);
                if(channelFuture.isSuccess()){
                    log.info("ok了----------------");
                }

            } catch (InterruptedException e) {
               log.info("有异常",e);
            }



            log.info("server启动了 {}", port);
            //等待服务端监听端口关闭
           //future.channel().closeFuture().sync();//.sync()
        }catch (Exception e)
        {
            log.error("监听端口异常",e);
        }
        finally
        {
            //线程符，释放资源
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
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
