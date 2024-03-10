package github.bitsim.transport;

import github.bitsim.dto.RpcRequest;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
