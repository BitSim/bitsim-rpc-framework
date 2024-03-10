package github.bitsim.transport.socket;

import github.bitsim.transport.RpcClient;
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
import java.util.Arrays;

/**
 * @author BitSim
 * @version v1.0.0
 **/

public class SocketClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private final String host;
    private final int port;

    public SocketClient(String host,int port) {
        this.host=host;
        this.port=port;
    }


    public Object sendRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)) {
            //发送
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            //接收
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcResponse<?> rpcResponse = (RpcResponse<?>) objectInputStream.readObject();
            System.out.println(rpcResponse);
            if (!rpcResponse.isSuccess()) {
                logger.error(rpcResponse.getMessage());
                throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, rpcResponse.getMessage());
            }
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Failed to send request", e);
            return RpcResponse.fail(RpcResponseCode.FAIL).getData();
        }
    }
}
