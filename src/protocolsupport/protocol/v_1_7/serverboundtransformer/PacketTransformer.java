package protocolsupport.protocol.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public interface PacketTransformer {

	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException;

}
