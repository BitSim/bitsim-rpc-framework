package github.bitsim.transport.netty;

import github.bitsim.User;
import github.bitsim.dto.RpcRequest;
import github.bitsim.dto.RpcResponse;
import github.bitsim.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
public class NettyClient implements RpcClient {
    private final String host;

    private final int port;
    private static final Bootstrap bootstrap;

    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("decode", new NettyFastJsonDecode(RpcResponse.class));
                        ch.pipeline().addLast("encode", new NettyFastJsonEncode());
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        Object result=null;

        try{
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel=channelFuture.channel();
            channel.writeAndFlush(rpcRequest).addListener(future -> {
                if(future.isSuccess()){
                    log.info("客户端发送成功:{}",rpcRequest.toString());
                }else log.error("客户端发送失败:",future.cause());
            });
            channel.closeFuture().sync();
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            RpcResponse rpcResponse = channel.attr(key).get();
            result=rpcResponse.getData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
