package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;

public interface PacketTransformer {

	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException;

}
