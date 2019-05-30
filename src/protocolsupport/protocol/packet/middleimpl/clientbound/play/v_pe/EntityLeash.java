package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class EntityLeash extends MiddleEntityLeash {

	public EntityLeash(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity wentity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (wentity == null) {
			return RecyclableEmptyList.get();
		}
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(EntityMetadata.createFaux(connection.getVersion(), cache.getAttributesCache().getLocale(), wentity));
		if (vehicleId == -1) {
			packets.add(EntityStatus.create(wentity.getId(), EntityStatus.UNLEASH));
		}
		return packets;
	}

}
