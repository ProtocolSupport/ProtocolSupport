package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class SpawnGlobal extends MiddleSpawnGlobal<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeByte(type);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, serializer));
	}

}
