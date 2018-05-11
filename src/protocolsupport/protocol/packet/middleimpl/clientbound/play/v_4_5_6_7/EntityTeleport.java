package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

/**
 * Created by JunHyeong Lim on 2018-05-12
 */
public class EntityTeleport extends MiddleEntityTeleport {
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity watchedEntity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		y *= 32;
		if (watchedEntity != null) {
			switch (watchedEntity.getType()) {
				case TNT:
				case FALLING_OBJECT:
				case MINECART:
					y += 16;
					break;
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
