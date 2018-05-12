package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity watchedEntity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		y *= 32;
		if (watchedEntity != null) {
			NetworkEntityType entityType = watchedEntity.getType();
			if (entityType == NetworkEntityType.MINECART ||
				(entityType == NetworkEntityType.TNT || entityType == NetworkEntityType.FALLING_OBJECT
					&& connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5))) {
				y += 16;
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID);
		serializer.writeInt(entityId);
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) y);
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		return RecyclableSingletonList.create(serializer);
	}
}
