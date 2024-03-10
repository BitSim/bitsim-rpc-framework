package github.bitsim;

import github.bitsim.registry.DefaultServiceRegistry;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.service.NewMyServiceImpl;
import github.bitsim.transport.socket.SocketServer;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class SocketServerApplication {
    public static void main(String[] args) {
        NewMyService newMyService=new NewMyServiceImpl();
        ServiceRegistry serviceRegistry=new DefaultServiceRegistry();
        serviceRegistry.register(newMyService);

        SocketServer rpcServer=new SocketServer(serviceRegistry);
        rpcServer.start(9999);
    }
}
