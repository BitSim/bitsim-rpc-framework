package github.bitsim;

import github.bitsim.transport.RpcClient;
import github.bitsim.transport.RpcClientProxy;
import github.bitsim.transport.netty.NettyClient;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class NettyClientApplication {
    public static void main(String[] args) {
        RpcClient rpcClient=new NettyClient("localhost",9999);

        RpcClientProxy rpcClientProxy=new RpcClientProxy(rpcClient);

        NewMyService newMyService=rpcClientProxy.getProxy(NewMyService.class);

        System.out.println(newMyService.login(new User("1","2")));
    }
}
