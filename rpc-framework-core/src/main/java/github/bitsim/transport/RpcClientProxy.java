package github.bitsim.transport;

import github.bitsim.dto.RpcRequest;
import github.bitsim.transport.socket.SocketClient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {
    private String host;
    private int port;
    private RpcClient rpcClient;
    public RpcClientProxy(RpcClient rpcClient){
        this.rpcClient=rpcClient;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, RpcClientProxy.this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest rpcRequest = RpcRequest.builder().
                interfaceName(method.getDeclaringClass().getName()).
                methodName(method.getName()).
                parameters(args).
                parameterTypes(method.getParameterTypes()).
                build();
        return rpcClient.sendRequest(rpcRequest);
    }
}
