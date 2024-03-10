package github.bitsim;

import github.bitsim.transport.RpcClient;
import github.bitsim.transport.RpcClientProxy;
import github.bitsim.transport.socket.SocketClient;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class SocketClientApplication {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;

    public static void main(String[] args) {
        RpcClient rpcClient = new SocketClient(HOST, PORT);
        RpcClientProxy rpcClientProxy=new RpcClientProxy(rpcClient);
        NewMyService newMyService=rpcClientProxy.getProxy(NewMyService.class);
        User user=new User("BitSim","123");
        System.out.println(newMyService.login(user));
    }
}
