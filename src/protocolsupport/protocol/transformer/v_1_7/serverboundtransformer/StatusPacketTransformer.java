package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import java.io.IOException;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException {
		packet.a(serializer);
	}

}
