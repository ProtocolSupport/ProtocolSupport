package protocolsupport.protocol.packet.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketPrepender implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		int readableBytes = input.readableBytes();
		int varIntLength = net.minecraft.server.v1_9_R1.PacketDataSerializer.a(readableBytes);
		if (varIntLength > 3) {
			throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
		}
		output.ensureWritable(varIntLength + readableBytes);
		ChannelUtils.writeVarInt(output, readableBytes);
		output.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
