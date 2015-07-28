package com.github.shevchik.protocolsupport.protocol.core.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import com.github.shevchik.protocolsupport.protocol.core.IPacketPrepender;

public class WrappedPrepender extends MessageToByteEncoder<ByteBuf> {

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
		realPrepender.prepend(ctx, input, output);
	}

}
