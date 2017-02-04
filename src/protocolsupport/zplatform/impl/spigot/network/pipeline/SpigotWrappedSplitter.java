package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.pipeline.common.FakeFrameDecoder;

public class SpigotWrappedSplitter extends ByteToMessageDecoder {

	private IPacketSplitter realSplitter = new FakeFrameDecoder();

	public void setRealSplitter(IPacketSplitter realSplitter) {
		this.realSplitter = realSplitter;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) {
		if (!input.isReadable()) {
			return;
		}
		realSplitter.split(ctx, input, list);
	}

}
