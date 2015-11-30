package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import java.io.IOException;
import java.util.Collection;

import io.netty.channel.Channel;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.PacketDataSerializer;

public interface PacketTransformer {

	public Collection<Packet<?>> transform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException, Exception;

}
