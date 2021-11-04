package protocolsupport.protocol.pipeline.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketFrameDecoder;

public class FakeFrameDecoder implements IPacketFrameDecoder {

	@Override
	public void decodeFrame(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) {
	}

}
