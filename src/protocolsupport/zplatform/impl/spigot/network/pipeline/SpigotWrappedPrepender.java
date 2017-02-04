package protocolsupport.zplatform.impl.spigot.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.common.FakeFrameEncoder;

public class SpigotWrappedPrepender extends MessageToByteEncoder<ByteBuf> {

	private IPacketPrepender realPrepender = new FakeFrameEncoder();

	public void setRealPrepender(IPacketPrepender realPrepender) {
		this.realPrepender = realPrepender;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) {
		if (!input.isReadable()) {
			return;
		}
		realPrepender.prepend(ctx, input, output);
	}

}
