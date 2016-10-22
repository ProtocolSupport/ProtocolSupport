package protocolsupport.protocol.pipeline.wrapped;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class WrappedSplitter extends ByteToMessageDecoder {

	private IPacketSplitter realSplitter = new IPacketSplitter() {
		@Override
		public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list)  {
		}
	};

	public void setRealSplitter(IPacketSplitter realSplitter) {
		this.realSplitter = realSplitter;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list)  {
		if (!input.isReadable()) {
			return;
		}
		realSplitter.split(ctx, input, list);
	}

}
