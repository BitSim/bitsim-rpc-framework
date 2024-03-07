package github.bitsim.socket;

import github.bitsim.dto.RpcRequest;
import github.bitsim.dto.RpcResponse;
import github.bitsim.enumerate.RpcResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class RpcRequestThreadHandler {
    private final Logger logger = LoggerFactory.getLogger(RpcRequestThreadHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeRequestMethod(rpcRequest, service);
            logger.info("{} 成功执行方法 {}", service.getClass().getCanonicalName(), rpcRequest.getMethodName());

        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
            logger.error(service.getClass().getCanonicalName() + e);

        }
        return result;
    }

    private Object invokeRequestMethod(RpcRequest rpcRequest, Object service) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        //RpcRequest获取了接口各种方法然后在Server中运行
        Class<?> targetClass = Class.forName(rpcRequest.getInterfaceName());
        if (!targetClass.isAssignableFrom(service.getClass())) {
            return RpcResponse.fail(RpcResponseCode.NOT_FOUND_CLASS);
        }
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        return RpcResponse.success(method.invoke(service, rpcRequest.getParameters()));

    }
}
