package protocolsupport.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IPacketPrepender {

	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output);

	public ByteBuf allocBuffer(ChannelHandlerContext ctx, ByteBuf in, boolean preferDirect);

}
