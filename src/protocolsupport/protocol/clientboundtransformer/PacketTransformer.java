package protocolsupport.protocol.clientboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;

public interface PacketTransformer {

	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException;

}
