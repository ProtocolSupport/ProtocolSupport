package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyDataWatcherSerializer;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnLiving extends MiddleSpawnLiving<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (type == 30) { //skip armor stand
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_LIVING_ID, version);
		serializer.writeInt(entityId);
		serializer.writeByte(IdRemapper.ENTITY_LIVING.getTable(version).getRemap(type));
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeByte(headPitch);
		serializer.writeShort(motX);
		serializer.writeShort(motY);
		serializer.writeShort(motZ);
		LegacyDataWatcherSerializer.encodeData(WatchedDataRemapper.transform(cache, entityId, metadata, version), serializer);
		return RecyclableSingletonList.create(serializer);
	}

}
