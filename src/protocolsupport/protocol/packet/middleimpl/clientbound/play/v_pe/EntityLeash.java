package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityLeash extends MiddleEntityLeash{

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		DataCache data = cache.getWatchedEntity(entityId).getDataCache();
		data.attachedId = vehicleId;
		cache.updateWatchedDataCache(entityId, data);
		return RecyclableSingletonList.create(EntityMetadata.create(cache.getWatchedEntity(entityId), version));
	}

}
