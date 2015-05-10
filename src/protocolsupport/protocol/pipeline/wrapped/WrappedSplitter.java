package protocolsupport.protocol.pipeline.wrapped;

import java.util.List;

import protocolsupport.protocol.pipeline.IPacketSplitter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class WrappedSplitter extends ByteToMessageDecoder {

	private IPacketSplitter realSplitter;

	public void setRealSplitter(IPacketSplitter realSplitter) {
		this.realSplitter = realSplitter;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		realSplitter.split(ctx, input, list);
	}

}
