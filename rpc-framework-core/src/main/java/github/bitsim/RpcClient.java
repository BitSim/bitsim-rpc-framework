package github.bitsim;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author BitSim
 * @version v1.0.0
 **/

public class RpcClient {
    public static Object sendRequest(String host,int port,RpcRequest rpcRequest){
        try(Socket socket=new Socket(host,port)){
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            RpcResponse<?> rpcResponse=(RpcResponse<?>) objectInputStream.readObject();
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
            return RpcResponse.fail(RpcResponseCode.FAIL).getData();
        }
    }
}
