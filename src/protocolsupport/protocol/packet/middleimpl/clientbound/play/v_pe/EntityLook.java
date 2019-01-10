package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityLook extends MiddleEntityLook {

	public EntityLook(ConnectionImpl connection) {
		super(connection);
	}

	public static final int ROTATION_FLAG = 0x08 | 0x10 | 0x20; //All three rotations.

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		if (entity.getType() == NetworkEntityType.PLAYER) {
			//Player uses special animation mode pitch to only update pitch and headyaw. Yaw is updated by extra teleport packets from entitytracker.
			return RecyclableSingletonList.create(SetPosition.create(entity, 0, 0, 0, pitch, yaw, SetPosition.ANIMATION_MODE_PITCH));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			serializer.writeByte(ROTATION_FLAG);
			serializer.writeByte(pitch);
			serializer.writeByte(yaw);
			serializer.writeByte(yaw); //headYaw
			return RecyclableSingletonList.create(serializer);
		}
	}

}
