package github.bitsim.transport.netty;

import github.bitsim.dto.RpcRequest;
import github.bitsim.dto.RpcResponse;
import github.bitsim.util.FastJsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    @SuppressWarnings("unchecked")
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse rpcResponse=(RpcResponse)msg;
        // 声明一个 AttributeKey 对象
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
        // 将服务端的返回结果保存到 AttributeMap 上，AttributeMap 可以看作是一个Channel的共享数据源
        // AttributeMap的key是AttributeKey，value是Attribute
        ctx.channel().attr(key).set(rpcResponse);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
