package protocolsupport.protocol.v_1_7.clientboundtransformer;

import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public void tranform(ChannelHandlerContext ctx, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		serializer.writeVarInt(packetId);
		packet.b(serializer);
	}

}
