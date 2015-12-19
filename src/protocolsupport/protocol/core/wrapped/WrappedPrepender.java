package protocolsupport.protocol.core.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.protocol.core.IPacketPrepender;

public class WrappedPrepender extends MessageToByteEncoder<ByteBuf> {

	public WrappedPrepender() {
		super(true);
	}

	private IPacketPrepender realPrepender = new IPacketPrepender() {
		@Override
		public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		}
	};

	public void setRealPrepender(IPacketPrepender realEncoder) {
		this.realPrepender = realEncoder;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		realPrepender.prepend(ctx, input, output);
	}

}
