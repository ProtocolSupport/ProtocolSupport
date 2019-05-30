package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityHeadRotation extends MiddleEntityHeadRotation {

	public EntityHeadRotation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		if (entity.getDataCache().isRiding()) {
			NetworkEntity vehicle = cache.getWatchedEntityCache().getWatchedEntity(entity.getDataCache().getVehicleId());
			if (vehicle != null && vehicle.getType() == NetworkEntityType.BOAT) {
				headRot -= vehicle.getDataCache().getYaw();
			}
		}
		//TODO: cows, pigs and some other entities dont use the sent head rotation, but some do. why?
		//    i suspect RESET_ROTATION maybe has something to do with it.
		entity.getDataCache().setHeadRotation(headRot);
		if (entity.getType() == NetworkEntityType.PLAYER || entity.getType().isOfType(NetworkEntityType.ARROW)) {
			//Players cannot be send with entitydelta, the entitytracker is modified to send extra teleport packet.
			//Arrows have weird values from java server. Their updates will not be send also.
			return RecyclableSingletonList.create(SetPosition.createPitch(entity));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			serializer.writeByte(EntityLook.FLAG_HAS_HEAD_YAW);
			serializer.writeByte(headRot);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
