package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class VarIntFrameEncoder implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output)  {
		int readableBytes = input.readableBytes();
		output.ensureWritable(readableBytes + 3);
		VarNumberSerializer.writeVarInt(output, readableBytes);
		output.writeBytes(input);
	}

}
