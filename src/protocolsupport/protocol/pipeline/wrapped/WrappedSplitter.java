package protocolsupport.protocol.pipeline.wrapped;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocolsupport.protocol.pipeline.IPacketSplitter;

public class WrappedSplitter extends ByteToMessageDecoder {

	private IPacketSplitter realSplitter = new IPacketSplitter() {
		@Override
		public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		}
	};

	public void setRealSplitter(IPacketSplitter realSplitter) {
		this.realSplitter = realSplitter;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		realSplitter.split(ctx, input, list);
	}

}
