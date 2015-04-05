package protocolsupport.protocol.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import protocolsupport.protocol.PublicPacketDecoder;

public class WrappedDecoder extends ByteToMessageDecoder {

	private PublicPacketDecoder realDecoder;

	public void setRealDecoder(PublicPacketDecoder realDecoder) {
		this.realDecoder = realDecoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		realDecoder.publicDecode(ctx, input, list);
	}

}
