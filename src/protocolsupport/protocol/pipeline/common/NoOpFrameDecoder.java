package protocolsupport.protocol.pipeline.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.pipeline.IPacketSplitter;

public class NoOpFrameDecoder implements IPacketSplitter {

	@Override
	public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		list.add(input.readBytes(input.readableBytes()));
	}

}
