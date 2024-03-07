package github.bitsim;

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
public class RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final ExecutorService threadPoolExecutor;

    public RpcServer() {
        BlockingQueue<Runnable> blockingQueue = new SynchronousQueue<>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        this.threadPoolExecutor = new ThreadPoolExecutor(
                10, 100, 1, TimeUnit.MINUTES, blockingQueue, threadFactory
        );
    }

    public void register(int port, Object service) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("socket服务启动成功......");
            Socket socket;
            while((socket=serverSocket.accept())!=null){
                logger.info("客户端连接成功......");
                threadPoolExecutor.execute(new WorkerThread(socket,service));
            }
        } catch (IOException e) {
            logger.error("socket error:" + e);
        }
    }

    public static void main(String[] args) {
        RpcServer rpcServer=new RpcServer();
        rpcServer.register(9999,"1");
    }


}
