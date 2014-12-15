package protocolsupport.protocol.fake;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class FakeSplitter extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf arg1, List<Object> arg2) throws Exception {
	}

}
