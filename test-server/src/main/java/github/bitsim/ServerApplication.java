package github.bitsim;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class ServerApplication {
    public static void main(String[] args) {
        RpcServer rpcServer=new RpcServer();
        NewMyService newMyService=new NewMyServiceImpl();
        rpcServer.register(9999,newMyService);
    }
}
