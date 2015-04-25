package protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public Packet<PacketListener>[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException {
		return null;
	}

}
