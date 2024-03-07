package github.bitsim;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class ClientApplication {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;

    public static void main(String[] args) {

        RpcClientProxy rpcClientProxy = new RpcClientProxy(HOST, PORT);
        NewMyService newMyService=rpcClientProxy.getProxy(NewMyService.class);
        User user=new User("BitSim","123");
        System.out.println(newMyService.login(user));
    }
}
