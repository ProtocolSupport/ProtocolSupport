package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.LegacyDataWatcherSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		TIntObjectMap<DataWatcherObject<?>> remapped = WatchedDataRemapper.transform(storage.getWatchedEntity(entityId), metadata, version);
		if (remapped.isEmpty()) {
			return RecyclableEmptyList.get();
		} else {
			PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_METADATA_ID, version);
			serializer.writeInt(entityId);
			serializer.writeBytes(LegacyDataWatcherSerializer.encodeData(version, remapped));
			return RecyclableSingletonList.create(serializer);
		}
	}

}
