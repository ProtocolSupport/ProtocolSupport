package protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.utils.PacketCreator;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public Collection<Packet<?>> tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException, IllegalAccessException, InstantiationException {
		switch (packetId) {
			case 0xFC: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.LOGIN_ENCRYPTION_BEGIN.get(), serializer));
			}
		}
		return null;
	}

}
