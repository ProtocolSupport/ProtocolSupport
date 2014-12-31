package protocolsupport.protocol.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) {
		return false;
	}

}
