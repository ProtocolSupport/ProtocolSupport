package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocolsupport.protocol.pipeline.IPacketFrameDecoder;
import protocolsupport.protocol.pipeline.common.FakeFrameDecoder;

public class SpigotWrappedFrameDecoder extends ByteToMessageDecoder {

	private IPacketFrameDecoder decoder = new FakeFrameDecoder();

	public void setDecoder(IPacketFrameDecoder decoder) {
		this.decoder = decoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) {
		if (!input.isReadable()) {
			return;
		}
		decoder.decodeFrame(ctx, input, list);
	}

}
