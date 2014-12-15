package protocolsupport.protocol.fake;

import net.minecraft.server.v1_8_R1.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FakeEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext arg0, Packet arg1, ByteBuf arg2) throws Exception {
		throw new Exception("Fake pipeline element is still in pipeline");
	}

}
