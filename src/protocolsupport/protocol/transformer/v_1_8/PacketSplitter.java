package protocolsupport.protocol.transformer.v_1_8;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import protocolsupport.protocol.core.IPacketSplitter;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketSplitter implements IPacketSplitter {

	private final byte[] array = new byte[3];

	@Override
	public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		input.markReaderIndex();
        for (int i = 0; i < array.length; ++i) {
            if (!input.isReadable()) {
            	input.resetReaderIndex();
                return;
            }
            array[i] = input.readByte();
            if (array[i] >= 0) {
                int length = ChannelUtils.readVarInt(Unpooled.wrappedBuffer(array));
                if (input.readableBytes() < length) {
                	input.resetReaderIndex();
                    return;
                } else {
	                list.add(input.readBytes(length));
	                return;
                }
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
	}

}
