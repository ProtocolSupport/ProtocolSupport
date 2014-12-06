package protocolsupport.protocol.serverboundtransformer;

import protocolsupport.protocol.PacketDataSerializer;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) {
		return false;
	}

}
