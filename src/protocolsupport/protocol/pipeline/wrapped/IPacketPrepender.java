package protocolsupport.protocol.pipeline.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IPacketPrepender {

	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) ;

}
