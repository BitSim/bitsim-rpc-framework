package github.bitsim;

import github.bitsim.registry.DefaultServiceRegistry;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.service.NewMyServiceImpl;
import github.bitsim.transport.RpcServer;
import github.bitsim.transport.netty.NettyServer;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class NettyServerApplication {
    public static void main(String[] args) {
        NewMyService newMyService=new NewMyServiceImpl();
        ServiceRegistry serviceRegistry=new DefaultServiceRegistry();
        serviceRegistry.register(newMyService);
        RpcServer rpcServer=new NettyServer(serviceRegistry);
        rpcServer.start(9999);
    }
}
