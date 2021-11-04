package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketFrameEncoder;

public class FakeFrameEncoder implements IPacketFrameEncoder {

	@Override
	public void encodeFrame(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) {
	}

	@Override
	public ByteBuf allocBuffer(ChannelHandlerContext ctx, ByteBuf in, boolean preferDirect) {
		return Unpooled.EMPTY_BUFFER;
	}

}
