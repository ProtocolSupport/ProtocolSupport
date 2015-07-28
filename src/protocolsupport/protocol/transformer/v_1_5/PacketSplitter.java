package protocolsupport.protocol.transformer.v_1_5;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.core.IPacketSplitter;

public class PacketSplitter implements IPacketSplitter {

	@Override
	public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		list.add(input.readBytes(input.readableBytes()));
	}

}
