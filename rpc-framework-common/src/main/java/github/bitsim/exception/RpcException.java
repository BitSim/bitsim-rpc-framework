package github.bitsim.exception;

import github.bitsim.enumerate.RpcErrorMessageEnum;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class RpcException extends RuntimeException{
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

}
