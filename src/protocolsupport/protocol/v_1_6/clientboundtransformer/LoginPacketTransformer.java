package protocolsupport.protocol.v_1_6.clientboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
	}

}
