package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

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
		NetworkEntity wentity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		y *= 32;
		if (wentity != null) {
			if (wentity.getType() == NetworkEntityType.TNT ||wentity.getType() == NetworkEntityType.FALLING_OBJECT) {
				y += 16;
			} else if (wentity.getType() == NetworkEntityType.MINECART) {
				y += 0.5;
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
