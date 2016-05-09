package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnGlobal extends MiddleSpawnGlobal<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, version);
		serializer.writeInt(entityId);
		serializer.writeByte(type);
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		return RecyclableSingletonList.create(serializer);
	}

}
