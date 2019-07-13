package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityLook extends MiddleEntityLook {

	public static final int FLAG_HAS_X     =       0b1;
	public static final int FLAG_HAS_Y     =      0b10;
	public static final int FLAG_HAS_Z     =     0b100;
	public static final int FLAG_HAS_ROT_X =    0b1000;
	public static final int FLAG_HAS_ROT_Y =   0b10000;
	public static final int FLAG_HAS_ROT_Z =  0b100000;
	public static final int FLAG_ONGROUND  = 0b1000000;

	// bytes must be added in to packet in order of X, Y, Z
	public static final int FLAG_HAS_PITCH = FLAG_HAS_ROT_X;
	public static final int FLAG_HAS_YAW = FLAG_HAS_ROT_Y;
	public static final int FLAG_HAS_HEAD_YAW = FLAG_HAS_ROT_Z;

	public static final int FLAG_HAS_COORDS = FLAG_HAS_X | FLAG_HAS_Y | FLAG_HAS_Z;

	public EntityLook(ConnectionImpl connection) {
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
			return RecyclableSingletonList.create(SetPosition.createPitch(entity));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			serializer.writeByte(FLAG_HAS_PITCH | FLAG_HAS_YAW | (onGround ? FLAG_ONGROUND : 0));
			serializer.writeByte(pitch);
			serializer.writeByte(yaw);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
