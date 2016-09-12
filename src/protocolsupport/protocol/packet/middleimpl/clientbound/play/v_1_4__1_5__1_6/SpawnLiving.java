package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyDataWatcherSerializer;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnLiving extends MiddleSpawnLiving<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (type == 30) { //skip armor stand
			return RecyclableEmptyList.get();
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_LIVING_ID, version);
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
		serializer.writeBytes(LegacyDataWatcherSerializer.encodeData(version, WatchedDataRemapper.transform(wentity, metadata, version)));
		return RecyclableSingletonList.create(serializer);
	}

}
