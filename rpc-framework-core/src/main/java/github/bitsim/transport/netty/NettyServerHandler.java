package github.bitsim.transport.netty;

import com.alibaba.fastjson2.JSONObject;
import github.bitsim.dto.RpcRequest;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.transport.RpcRequestThreadHandler;
import github.bitsim.util.FastJsonUtil;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final ServiceRegistry serviceRegistry;

    NettyServerHandler(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("NettyServer接收到了:{}", msg);
            RpcRequest rpcRequest = (RpcRequest) msg;
            FastJsonUtil.JsonObjectToJavaObject(rpcRequest);
            RpcRequestThreadHandler rpcRequestThreadHandler = new RpcRequestThreadHandler();
            Object result = rpcRequestThreadHandler.handle(rpcRequest, serviceRegistry.getService(rpcRequest.getInterfaceName()));
            log.info("业务返回结果:{}",result);
            ctx.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }



}
