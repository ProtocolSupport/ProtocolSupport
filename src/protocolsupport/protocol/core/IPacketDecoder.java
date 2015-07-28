package protocolsupport.protocol.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface IPacketDecoder {

	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception;

}
