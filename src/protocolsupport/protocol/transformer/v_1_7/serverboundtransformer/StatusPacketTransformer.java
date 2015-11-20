package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import io.netty.channel.Channel;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.utils.PacketCreator;

public class StatusPacketTransformer implements PacketTransformer {

	@Override
	public Collection<Packet<?>> tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException, IllegalAccessException, InstantiationException {
		return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.get(EnumProtocol.STATUS, packetId), serializer));
	}

}
