package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData.Offset;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {

	public EntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		byte headYaw = entity.getDataCache().getHeadRotation(yaw);
		PEEntityData typeData = PEDataValues.getEntityData(entity.getType());
		if ((typeData != null) && (typeData.getOffset() != null)) {
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
					//This bunch calculates the relative head position. Apparently the player is a bit turned inside the boat (in PE) so another little offset is needed.
					float relYaw = (float) ((headYaw - vehicle.getDataCache().getHeadRotation(yaw)) + 11.38);
					if (relYaw <= -128) { relYaw += 256; }
					if (relYaw > 128) { relYaw -= 256; }
					return RecyclableSingletonList.create(updateGeneral(version, entity, x, y, z, pitch, (byte) relYaw, (byte) 0, onGround, false));
				}
			} else {
				//If the vehicle is killed a unlink might not be sent yet.
				entity.getDataCache().setVehicleId(0);
			}
		}
		if (entity.getType() == NetworkEntityType.BOAT) {
			entity.getDataCache().setHeadRotation(yaw);
		}
		return RecyclableSingletonList.create(updateGeneral(version, entity, x, y, z, pitch, yaw, headYaw, onGround, false));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, NetworkEntity entity, double x, double y, double z, byte pitch, byte yaw, byte headYaw, boolean onGround, boolean teleported) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_TELEPORT);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		int flag = onGround ? (teleported ? 192 : 128) : (teleported ? 64 : 0);
		serializer.writeByte(flag);
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeByte(headYaw);
		return serializer;
	}

	public static ClientBoundPacketData updateGeneral(ProtocolVersion version, NetworkEntity entity, double x, double y, double z, byte pitch, byte yaw, byte headYaw, boolean onGround, boolean teleported) {
		if (entity.getType() == NetworkEntityType.PLAYER) {
			return SetPosition.create(entity, x, y, z, pitch * 360.F / 256.F, yaw * 360.F / 256.F, headYaw * 360.F / 256.F, teleported ? SetPosition.ANIMATION_MODE_TELEPORT : SetPosition.ANIMATION_MODE_ALL);
		} else {
			return create(version, entity, x, y, z, pitch, yaw, headYaw, onGround, teleported);
		}
	}

}
