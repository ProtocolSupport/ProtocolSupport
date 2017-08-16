package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityLeash extends MiddleEntityLeash {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity e = cache.getWatchedEntity(entityId);
		if(e != null) {
			packets.add(EntityMetadata.createFaux(e, cache.getLocale(), connection.getVersion()));
			if(vehicleId == -1) {
				packets.add(EntityStatus.create(e, EntityStatus.PE_UNLEASH, connection.getVersion()));
			}
		}
		return packets;
	}

}
