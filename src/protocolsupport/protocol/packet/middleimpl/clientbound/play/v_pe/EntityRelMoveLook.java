package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMoveLook extends MiddleEntityRelMoveLook {

	public EntityRelMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		PEDataValues.PEEntityData typeData = PEDataValues.getEntityData(entity.getType());
		if (typeData != null && typeData.getOffset() != null) {
			PEDataValues.PEEntityData.Offset offset = typeData.getOffset();
			pitch += offset.getPitch();
			yaw += offset.getYaw();
		}
		entity.getDataCache().setYaw(yaw);
		entity.getDataCache().setPitch(pitch);
		if (entity.getType() == NetworkEntityType.PLAYER) {
			EntityRelMove.updateRelMove(entity, relX, relY, relZ);
			return RecyclableSingletonList.create(SetPosition.createAll(entity));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			serializer.writeByte(EntityLook.FLAG_HAS_COORDS | EntityLook.FLAG_HAS_PITCH
				| EntityLook.FLAG_HAS_YAW | (onGround ? EntityLook.FLAG_ONGROUND : 0));
			EntityRelMove.writeAndUpdateRelMove(serializer, entity, relX, relY, relZ);
			serializer.writeByte(pitch);
			serializer.writeByte(yaw);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
