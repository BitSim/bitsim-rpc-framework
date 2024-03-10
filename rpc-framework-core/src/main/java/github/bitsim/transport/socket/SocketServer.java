package github.bitsim.transport.socket;

import github.bitsim.transport.RpcServer;
import github.bitsim.registry.ServiceRegistry;
import github.bitsim.transport.RpcRequestThreadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @author BitSim
 * @version v1.0.0
 **/
public class SocketServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPoolExecutor;
    private final ServiceRegistry serviceRegistry;
    private final RpcRequestThreadHandler rpcRequestThreadHandler=new RpcRequestThreadHandler();

    public SocketServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> blockingQueue = new SynchronousQueue<>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        this.threadPoolExecutor = new ThreadPoolExecutor(
                10, 100, 1, TimeUnit.MINUTES, blockingQueue, threadFactory
        );
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("socket服务启动成功......");
            Socket socket;
            while((socket=serverSocket.accept())!=null){
                logger.info("客户端连接成功......");
                threadPoolExecutor.execute(new RpcRequestThread(socket, rpcRequestThreadHandler,serviceRegistry));
            }
        } catch (IOException e) {
            logger.error("socket error:" + e);
        }
    }
}
