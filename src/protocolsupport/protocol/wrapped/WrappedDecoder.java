package protocolsupport.protocol.wrapped;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
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
