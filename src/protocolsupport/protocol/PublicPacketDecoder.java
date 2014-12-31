package protocolsupport.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface PublicPacketDecoder {

	public void publicDecode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception;

}
