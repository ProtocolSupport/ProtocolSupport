package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData.PocketOffset;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		NetworkEntity entity = cache.getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		byte headYaw = entity.getDataCache().getHeadRotation(yaw);
		PocketEntityData typeData = PocketData.getPocketEntityData(entity.getType());
		if (typeData != null) {
			if (typeData.getOffset() != null) {
				PocketOffset offset = typeData.getOffset();
				x += offset.getX();
				y += offset.getY();
				z += offset.getZ();
				pitch += offset.getPitch();
				yaw += offset.getYaw();
			}
		}
		if (entity.getDataCache().riderInfo != null) {
			NetworkEntity vehicle = cache.getWatchedEntity(entity.getDataCache().riderInfo.getVehicleId());
			if (vehicle != null) {
				if (vehicle.getType() == NetworkEntityType.BOAT) {
					//This bunch calculates the relative head position. Apparently the player is a bit turned inside the boat (in PE) so another little offset is needed.
					float relYaw = (float) (headYaw - vehicle.getDataCache().getHeadRotation(yaw) + 11.38);
					if (relYaw <= -128) { relYaw += 256; } 
					if (relYaw > 128) { relYaw -= 256; }
					System.out.println("BOAT: " + vehicle.getDataCache().getHeadRotation(yaw));
					System.out.println("HEAD: " + headYaw);
					System.out.println("YAWY: " + relYaw);
					return RecyclableSingletonList.create(Position.create(version, entity, x, y, z, pitch, relYaw, Position.ANIMATION_MODE_ALL));
				}
			} else {
				entity.getDataCache().riderInfo = null;
			}
		}
		if(entity.getType() == NetworkEntityType.BOAT) {
			entity.getDataCache().setHeadRotation(yaw);
		}
		if (entity.getType() == NetworkEntityType.PLAYER) {
			return RecyclableSingletonList.create(Position.create(version, entity, x, y, z, pitch, yaw, Position.ANIMATION_MODE_ALL));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_TELEPORT, version);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			MiscSerializer.writeLFloat(serializer, (float) x);
			MiscSerializer.writeLFloat(serializer, (float) y);
			MiscSerializer.writeLFloat(serializer, (float) z);
			serializer.writeByte(pitch);
			serializer.writeByte(headYaw);
			serializer.writeByte(yaw);
			serializer.writeBoolean(onGround);
			serializer.writeBoolean(false); //teleported
			return RecyclableSingletonList.create(serializer);
		}
	}

}
