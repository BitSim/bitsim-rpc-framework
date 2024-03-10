package github.bitsim.util;

import com.alibaba.fastjson2.JSONObject;
import github.bitsim.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
public class FastJsonUtil {
    /**
     * 解决FastJson反序列化过程中将成员变量转换成JsonObject的问题
     */
    public static void JsonObjectToJavaObject(RpcRequest rpcRequest){
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameters[i] instanceof JSONObject) {
                parameters[i] = ((JSONObject) parameters[i]).toJavaObject(parameterTypes[i]);
            }
            if (!parameterTypes[i].isInstance(parameters[i])) {
                log.error("Parameter type mismatch: expected {}, but was {}", parameterTypes[i], parameters[i].getClass());
            }
        }
    }


}
