package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.utils.PacketCreator;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public Collection<Packet<?>> transform(Channel channel, int packetId, PacketDataSerializer serializer) throws Exception {
		switch (packetId) {
			case 0x00: {
				PacketCreator creator = new PacketCreator(ServerBoundPacket.HANDSHAKE_START.get());
				serializer.readVarInt();
				creator.writeVarInt(ProtocolVersion.getLatest().getId());
				creator.writeString(serializer.readString(32767));
				creator.writeShort(serializer.readUnsignedShort());
				creator.writeVarInt(serializer.readVarInt());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			default: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerBoundPacket.get(EnumProtocol.HANDSHAKING, packetId), serializer));
			}
		}
	}

}
