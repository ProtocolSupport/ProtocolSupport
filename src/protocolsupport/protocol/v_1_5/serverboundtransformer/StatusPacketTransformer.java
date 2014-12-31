package protocolsupport.protocol.v_1_5.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public Packet[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException {
		return null;
	}

}
