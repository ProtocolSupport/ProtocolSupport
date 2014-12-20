package protocolsupport.protocol.v_1_5.serverboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;

public interface PacketTransformer {

	public Packet[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException;

}
