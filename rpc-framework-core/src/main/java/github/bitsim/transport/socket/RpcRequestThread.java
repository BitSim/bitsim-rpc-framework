package github.bitsim.transport.socket;

import github.bitsim.dto.RpcRequest;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.transport.RpcRequestThreadHandler;
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
public class RpcRequestThread implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(RpcRequestThread.class);
    private final Socket socket;
    private final RpcRequestThreadHandler rpcRequestThreadHandler;
    private final ServiceRegistry serviceRegistry;

    public RpcRequestThread(Socket socket, RpcRequestThreadHandler rpcRequestThreadHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.rpcRequestThreadHandler = rpcRequestThreadHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = rpcRequestThreadHandler.handle(rpcRequest, serviceRegistry.getService(rpcRequest.getInterfaceName()));
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(RpcRequestThread.class.getName() + ":" + e);
        }
    }


}
