package protocolsupport.protocol.transformer.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.core.IPacketPrepender;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketPrepender implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		final int readableBytes = input.readableBytes();
		final int varIntLength = net.minecraft.server.v1_8_R3.PacketDataSerializer.a(readableBytes);
		if (varIntLength > 3) {
			throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
		}
		output.ensureWritable(varIntLength + readableBytes);
		ChannelUtils.writeVarInt(output, readableBytes);
		output.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
