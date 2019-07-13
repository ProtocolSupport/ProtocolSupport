package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends MiddleEntityStatus {

	/* The UNLEASH entity status is sent from the EntityLeash packet */
	public static final int UNLEASH = 63;

	public EntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		NetworkEntityType entityType = entity.getType();
		int peStatus = PEDataValues.getEntityStatusRemap(status, entityType);
		if (peStatus == -1) {
			return RecyclableEmptyList.get();
		}
		return RecyclableSingletonList.create(create(entityId, peStatus));
	}

	public static ClientBoundPacketData create(int entityId, int status) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		serializer.writeByte((byte) status);
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		return serializer;
	}

}
