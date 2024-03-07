package github.bitsim.socket;

import github.bitsim.dto.RpcRequest;
import github.bitsim.dto.RpcResponse;
import github.bitsim.enumerate.RpcErrorMessageEnum;
import github.bitsim.enumerate.RpcResponseCode;
import github.bitsim.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author BitSim
 * @version v1.0.0
 **/

public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public static Object sendRequest(String host, int port, RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcResponse<?> rpcResponse = (RpcResponse<?>) objectInputStream.readObject();
            System.out.println(rpcResponse);
            if (!rpcResponse.isSuccess()) {
                logger.error(rpcResponse.getMessage());
                throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, rpcResponse.getMessage());
            }
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
            return RpcResponse.fail(RpcResponseCode.FAIL).getData();
        }
    }
}
