package github.bitsim.dto;

import github.bitsim.enumerate.RpcResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class RpcResponse<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    private boolean success;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCode.SUCCESS.getCode());
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(code.getCode());
        response.setMessage(code.getMessage());
        response.setSuccess(false);
        return response;
    }
}
