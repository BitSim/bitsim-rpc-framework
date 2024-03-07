package github.bitsim.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Getter
@AllArgsConstructor
public enum RpcErrorMessageEnum {
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    INTERFACE_NO_FOUND_SERVICE("没有找到对应的业务"),
    SERVICE_MISSING_INTERFACE("服务缺失接口");
    private final String message;
}
