package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_9_R2.PacketDataSerializer;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.utils.netty.ChannelUtils;

public class VarIntFrameEncoder implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		int readableBytes = input.readableBytes();
		int varIntLength = PacketDataSerializer.a(readableBytes);
		if (varIntLength > 3) {
			throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
		}
		output.ensureWritable(varIntLength + readableBytes);
		ChannelUtils.writeVarInt(output, readableBytes);
		output.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
