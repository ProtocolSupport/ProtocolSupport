package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) {
		return false;
	}

}
