package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityDestroy extends MiddleEntityDestroy {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ProtocolVersion version = connection.getVersion();
		for (int entityId : entityIds) {
			NetworkEntity entity = cache.getWatchedEntity(entityId);
			if((entity != null) && (entity.getType() == NetworkEntityType.ITEM)) {
					cache.removePreparedItem(entityId);
			}
			packets.add(create(version, entityId));
		}
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int entityId) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_DESTROY, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		return serializer;
	}

}
