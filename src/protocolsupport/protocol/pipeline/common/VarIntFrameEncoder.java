package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class VarIntFrameEncoder implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) {
		VarNumberSerializer.writeVarInt(output, input.readableBytes());
		output.writeBytes(input);
	}

	@Override
	public ByteBuf allocBuffer(ChannelHandlerContext ctx, ByteBuf in, boolean preferDirect) {
		return ctx.alloc().heapBuffer(in.readableBytes() + 3);
	}

}
