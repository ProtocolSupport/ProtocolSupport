package protocolsupport.protocol.v_1_6.serverboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public Packet[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException {
		return null;
	}

}
