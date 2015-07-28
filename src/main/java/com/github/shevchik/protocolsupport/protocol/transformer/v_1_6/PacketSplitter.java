package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import com.github.shevchik.protocolsupport.protocol.core.IPacketSplitter;

public class PacketSplitter implements IPacketSplitter {

	@Override
	public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		list.add(input.readBytes(input.readableBytes()));
	}

}
