package cc.sharper.bean;

import cc.sharper.util.io.HubbleMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端Channel
 * Created by liumin3 on 2016/9/14.
 */
public class HubbleClient  extends SimpleChannelInboundHandler<HubbleResponse>
{
    private static final Logger log = LoggerFactory.getLogger(HubbleClient.class);
    private String host;
    private int port;
    private HubbleResponse response;
    private final Object obj = new Object();

    public HubbleClient(String host, int port) {
        this.host = host;
        this.port = port;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HubbleResponse msg) throws Exception
    {
        this.response = msg;
        synchronized (obj) {
            obj.notifyAll(); // 收到响应，唤醒线程
        }
    }



    //发送请求信息
    public HubbleResponse send(HubbleRequest request) throws Exception
    {
        EventLoopGroup group = new NioEventLoopGroup();
        try
        {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception
                        {
                            channel.pipeline()
                                    .addLast(new HubbleEncoder(HubbleMessage.class)) //HubbleRequest
                                    .addLast(new HubbleDecoder(HubbleMessage.class)) //HubbleResponse
                                    .addLast(HubbleClient.this); //
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();//
            future.channel().writeAndFlush(request).sync();//发送请求信息去目标地方

            synchronized (obj)
            {
                obj.wait(); // 未收到响应，使线程等待
            }

            if (response != null)
            {
                future.channel().closeFuture().sync();//关闭管道符
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("发送请求异常",e);
        }
        finally
        {
            group.shutdownGracefully();//关闭客户端
        }
        return response;

    }

}
