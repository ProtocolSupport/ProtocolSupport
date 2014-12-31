package protocolsupport.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface PublicPacketDecoder {

	public void publicDecode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception;

}
