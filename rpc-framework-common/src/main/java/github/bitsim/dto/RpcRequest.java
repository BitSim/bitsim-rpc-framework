package github.bitsim.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
@Builder
public class RpcRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;
}
