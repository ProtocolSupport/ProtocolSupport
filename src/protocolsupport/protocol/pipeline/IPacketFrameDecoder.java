package protocolsupport.protocol.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IPacketFrameDecoder {

	public void decodeFrame(ChannelHandlerContext ctx, ByteBuf input, List<Object> list);

}
