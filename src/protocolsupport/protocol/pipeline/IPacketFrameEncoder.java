package protocolsupport.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IPacketFrameEncoder {

	public void encodeFrame(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output);

	public ByteBuf allocBuffer(ChannelHandlerContext ctx, ByteBuf in, boolean preferDirect);

}
