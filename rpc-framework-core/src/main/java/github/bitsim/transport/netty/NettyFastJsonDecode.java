package github.bitsim.transport.netty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class NettyFastJsonDecode extends ByteToMessageDecoder {
    private Class<?> aClass;
    NettyFastJsonDecode(Class<?>aClass){
        this.aClass=aClass;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        Object obj = JSON.parseObject(data, aClass, JSONReader.Feature.SupportClassForName);
        list.add(obj);
    }
}
