package protocolsupport.protocol.fake;

import protocolsupport.protocol.PublicPacketEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;

public class FakeEncoder extends MessageToByteEncoder<Packet<PacketListener>> implements PublicPacketEncoder {

	@Override
	protected void encode(ChannelHandlerContext arg0, Packet<PacketListener> arg1, ByteBuf arg2) throws Exception {
	}

	@Override
	public void publicEncode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
	}

}
