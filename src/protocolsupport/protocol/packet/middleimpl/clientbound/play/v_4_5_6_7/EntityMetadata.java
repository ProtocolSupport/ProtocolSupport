package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyDataWatcherSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ArrayMap<DataWatcherObject<?>> remapped = WatchedDataRemapper.transform(cache.getWatchedEntity(entityId), metadata, version);
		if (remapped == null) {
			return RecyclableEmptyList.get();
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_METADATA_ID, version);
			serializer.writeInt(entityId);
			LegacyDataWatcherSerializer.encodeData(serializer, version, cache.getLocale(), remapped);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
