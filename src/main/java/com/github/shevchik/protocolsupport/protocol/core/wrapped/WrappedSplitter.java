package com.github.shevchik.protocolsupport.protocol.core.wrapped;

import java.util.List;

import com.github.shevchik.protocolsupport.protocol.core.IPacketSplitter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

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
		realSplitter.split(ctx, input, list);
	}

}
