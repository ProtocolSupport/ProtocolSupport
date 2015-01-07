package protocolsupport.protocol.fake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import protocolsupport.protocol.PublicPacketDecoder;

public class FakeDecoder extends ByteToMessageDecoder implements PublicPacketDecoder {

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf arg1, List<Object> arg2) throws Exception {
	}

	@Override
	public void publicDecode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
	}

}
