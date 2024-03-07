package github.bitsim;

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
//        Object object=targetClass.newInstance();
//        Method method1=targetClass.getDeclaredMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
//        boolean result= (boolean) method1.invoke(object,rpcRequest.getParameters());
        return RpcClient.sendRequest(host, port, rpcRequest);
    }
}
