package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		TIntObjectMap<DataWatcherObject<?>> remapped = WatchedDataRemapper.transform(cache, entityId, metadata, version);
		if (remapped.isEmpty()) {
			return RecyclableEmptyList.get();
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_METADATA_ID, version);
			serializer.writeVarInt(entityId);
			DataWatcherDeserializer.encodeData(remapped, serializer);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
