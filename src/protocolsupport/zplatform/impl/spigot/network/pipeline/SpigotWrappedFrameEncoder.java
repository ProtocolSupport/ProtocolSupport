package protocolsupport.zplatform.impl.spigot.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.protocol.pipeline.IPacketFrameEncoder;
import protocolsupport.protocol.pipeline.common.FakeFrameEncoder;

public class SpigotWrappedFrameEncoder extends MessageToByteEncoder<ByteBuf> {

	private IPacketFrameEncoder encoder = new FakeFrameEncoder();

	public void setEncoder(IPacketFrameEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) {
		if (!input.isReadable()) {
			return;
		}
		encoder.encodeFrame(ctx, input, output);
	}

	@Override
	protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf input, boolean preferDirect) throws Exception {
		return encoder.allocBuffer(ctx, input, preferDirect);
	}

}
