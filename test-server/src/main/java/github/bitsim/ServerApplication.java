package github.bitsim;

import github.bitsim.registry.DefaultServiceRegistry;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.socket.RpcServer;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class ServerApplication {
    public static void main(String[] args) {
        NewMyService newMyService=new NewMyServiceImpl();
//        rpcServer.register(9999,newMyService);
        ServiceRegistry serviceRegistry=new DefaultServiceRegistry();
        serviceRegistry.register(newMyService);

        RpcServer rpcServer=new RpcServer(serviceRegistry);
        rpcServer.start(9999);
    }
}
