package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class EntityHeadRotation extends MiddleEntityHeadRotation {

	public EntityHeadRotation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity != null) {
			entity.getDataCache().setHeadRotation(headRot);
		}
		//TODO Figure out why this makes the entity disappear!
//		System.out.println("SENDING HEADROT TO PE: " + headRot);
//		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
//		VarNumberSerializer.writeVarLong(serializer, entityId);
//		serializer.writeByte((byte) 0x20);
//		serializer.writeByte(headYaw);
//		return RecyclableSingletonList.create(serializer);
		return RecyclableEmptyList.get();
	}

}
