package github.bitsim.transport.netty;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class NettyFastJsonEncode extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] data= JSON.toJSONString(o).getBytes();
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }

}
