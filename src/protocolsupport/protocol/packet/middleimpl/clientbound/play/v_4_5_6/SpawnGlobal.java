package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnGlobal extends MiddleSpawnGlobal {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, connection.getVersion());
		serializer.writeInt(entityId);
		serializer.writeByte(type);
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		return RecyclableSingletonList.create(serializer);
	}

}
