package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketFrameEncoder;

public class NoOpFrameEncoder implements IPacketFrameEncoder {

	@Override
	public void encodeFrame(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) {
		output.writeBytes(input);
	}

	@Override
	public ByteBuf allocBuffer(ChannelHandlerContext ctx, ByteBuf in, boolean preferDirect) {
		return ctx.alloc().heapBuffer(in.readableBytes());
	}

}
