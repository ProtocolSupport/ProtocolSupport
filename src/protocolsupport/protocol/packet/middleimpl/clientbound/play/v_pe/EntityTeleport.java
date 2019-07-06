package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData.Offset;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {

	public static final int FLAG_TELEPORTED = 0x1;
	public static final int FLAG_ONGROUND = 0x2;

	private static final byte RELATIVE_THRESHOLD = 5;

	public EntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	public static ClientBoundPacketData create(NetworkEntity entity, float x, float y, float z, byte pitch, byte yaw, byte headYaw, boolean onGround, boolean teleported) {
		// TODO: Cache relative movement, when relative packet translation is implemented
		final NetworkEntityDataCache cache = entity.getDataCache();

		final float oldX = cache.getPosX();
		final float oldY = cache.getPosY();
		final float oldZ = cache.getPosZ();

		cache.setPos(x, y, z);
		cache.setYaw(yaw);
		cache.setPitch(pitch);

		// Check if a relative movement is needed (Less than 5 blocks)
		if (!teleported && Math.abs(x - oldX) < RELATIVE_THRESHOLD && Math.abs(y - oldY) < RELATIVE_THRESHOLD && Math.abs(z - oldZ) < RELATIVE_THRESHOLD) {
			return serializeMoveEntityDelta(entity, x, y, z, oldX, oldY, oldZ, yaw, headYaw, pitch, onGround);
		}

		return serializeEntityTeleport(entity, x, y, z, pitch, yaw, headYaw, onGround, teleported);
	}

	private static ClientBoundPacketData serializeEntityTeleport(NetworkEntity entity, float x, float y, float z, byte pitch, byte yaw, byte headYaw, boolean onGround, boolean teleported) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_TELEPORT);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		byte flag = 0;
		flag |= teleported ? FLAG_TELEPORTED : 0;
		flag |= onGround ? FLAG_ONGROUND : 0;

		serializer.writeByte(flag);
		serializer.writeFloatLE(x);
		serializer.writeFloatLE(y);
		serializer.writeFloatLE(z);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeByte(headYaw);

		return serializer;
	}

	private static ClientBoundPacketData serializeMoveEntityDelta(NetworkEntity entity, float x, float y, float z, float oldX, float oldY, float oldZ, byte yaw, byte headYaw, byte pitch, boolean onGround) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());

		serializer.writeByte(EntityLook.FLAG_HAS_COORDS |
			EntityLook.FLAG_HAS_PITCH |
			EntityLook.FLAG_HAS_YAW |
			EntityLook.FLAG_HAS_HEAD_YAW |
			(onGround ? EntityLook.FLAG_ONGROUND : 0)
		);

		VarNumberSerializer.writeSVarInt(serializer, Float.floatToRawIntBits(x) - Float.floatToRawIntBits(oldX));
		VarNumberSerializer.writeSVarInt(serializer, Float.floatToRawIntBits(y) - Float.floatToRawIntBits(oldY));
		VarNumberSerializer.writeSVarInt(serializer, Float.floatToRawIntBits(z) - Float.floatToRawIntBits(oldZ));

		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeByte(headYaw);

		return serializer;
	}

	public static ClientBoundPacketData updateGeneral(NetworkEntity entity, float x, float y, float z, byte pitch, byte yaw, byte headYaw, boolean onGround, boolean teleported) {
		if (entity.getType() == NetworkEntityType.PLAYER) {
			return SetPosition.create(entity, x, y, z, pitch * 360.F / 256.F, yaw * 360.F / 256.F, headYaw * 360.F / 256.F,
				teleported ? SetPosition.ANIMATION_MODE_TELEPORT : SetPosition.ANIMATION_MODE_ALL);
		} else {
			return create(entity, x, y, z, pitch, yaw, headYaw, onGround, teleported);
		}
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		byte headYaw = entity.getDataCache().getHeadRotation(yaw);
		PEEntityData typeData = PEDataValues.getEntityData(entity.getType());
		if (typeData != null && typeData.getOffset() != null) {
			Offset offset = typeData.getOffset();
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			pitch += offset.getPitch();
			yaw += offset.getYaw();
		}

		if (entity.getDataCache().isRiding()) {
			NetworkEntity vehicle = cache.getWatchedEntityCache().getWatchedEntity(entity.getDataCache().getVehicleId());
			if (vehicle != null) {
				if (vehicle.getType() == NetworkEntityType.BOAT) {
					return RecyclableEmptyList.get();
				}
			} else {
				//If the vehicle is killed a unlink might not be sent yet.
				entity.getDataCache().setVehicleId(0);
			}
		}

		return RecyclableSingletonList.create(updateGeneral(entity, (float) x, (float) y, (float) z,
			pitch, yaw, headYaw, onGround, false));
	}

}
