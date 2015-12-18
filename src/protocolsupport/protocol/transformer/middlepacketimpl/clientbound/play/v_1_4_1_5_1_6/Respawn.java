package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class Respawn extends MiddleRespawn<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(dimension);
		serializer.writeByte(difficulty);
		serializer.writeByte(gamemode);
		serializer.writeShort(256);
		serializer.writeString(leveltype);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_RESPAWN_ID, serializer));
	}

}
