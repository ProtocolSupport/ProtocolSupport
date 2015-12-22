package protocolsupport.protocol.transformer.v_1_7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketPrepender;
import protocolsupport.utils.Utils;

public class PacketPrepender implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		int readableBytes = input.readableBytes();
		int varIntLength = PacketDataSerializer.a(readableBytes);
		if (varIntLength > 3) {
			throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
		}
		output.ensureWritable(varIntLength + readableBytes);
		Utils.writeVarInt(output, readableBytes);
		output.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
