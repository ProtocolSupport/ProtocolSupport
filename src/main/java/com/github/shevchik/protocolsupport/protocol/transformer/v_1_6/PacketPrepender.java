package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import com.github.shevchik.protocolsupport.protocol.core.IPacketPrepender;

public class PacketPrepender implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		int readableBytes = input.readableBytes();
		output.ensureWritable(readableBytes);
		output.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
