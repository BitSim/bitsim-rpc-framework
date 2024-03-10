package github.bitsim.transport.netty;

import github.bitsim.dto.RpcRequest;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.transport.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
public class NettyServer implements RpcServer {
    private final ServiceRegistry serviceRegistry;

    public NettyServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry=serviceRegistry;
    }

    @Override
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,64)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<>() {

                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline channelPipeline=channel.pipeline();
                            channelPipeline.addLast("decode",new NettyFastJsonDecode(RpcRequest.class));
                            channelPipeline.addLast("encode",new NettyFastJsonEncode());
                            channelPipeline.addLast(new NettyServerHandler(serviceRegistry));
                        }
                    });

            ChannelFuture channelFuture=serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


}
