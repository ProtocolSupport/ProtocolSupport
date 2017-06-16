package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		TIntObjectMap<DataWatcherObject<?>> remapped = WatchedDataRemapper.transform(cache.getWatchedEntity(entityId), metadata, version);
		if (remapped.isEmpty()) {
			return RecyclableEmptyList.get();
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_METADATA_ID, version);
			VarNumberSerializer.writeVarInt(serializer, entityId);
			DataWatcherDeserializer.encodeData(serializer, version, remapped);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
