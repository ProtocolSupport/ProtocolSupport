package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		NetworkEntity entity = cache.getWatchedEntity(entityId);
		if(entity == null || cache.isSelf(entityId)) {
			return RecyclableEmptyList.get();
		} else {
			return RecyclableSingletonList.create(create(entity, x, y, z, pitch, yaw, yaw, onGround, true, version));
		}
		
	}
	
	public static ClientBoundPacketData create(NetworkEntity entity, double x, double y, double z, byte pitch, byte headYaw, byte yaw, boolean onGround, boolean teleported, ProtocolVersion version) {
		if ((entity != null) && (entity.getType() == NetworkEntityType.PLAYER)) {
			return Position.create(version, entity.getId(), x, y + 1.6200000047683716D, z, pitch, headYaw, yaw, onGround, Position.ANIMATION_MODE_ALL);
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_MOVE, version);
			VarNumberSerializer.writeVarLong(serializer, entity.getId());
			MiscSerializer.writeLFloat(serializer, (float) x);
			MiscSerializer.writeLFloat(serializer, (float) y);
			MiscSerializer.writeLFloat(serializer, (float) z);
			serializer.writeByte(pitch);
			serializer.writeByte(headYaw);
			serializer.writeByte(yaw);
			serializer.writeBoolean(onGround);
			serializer.writeBoolean(teleported);
			return serializer;
		}
	}

}
