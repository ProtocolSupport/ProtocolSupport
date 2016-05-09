package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		WatchedEntity wentity = storage.getWatchedEntity(entityId);
		y *= 32;
		if ((wentity != null) && (wentity.getType() == SpecificType.TNT || wentity.getType() == SpecificType.FALLING_OBJECT)) {
			y += 16;
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, version);
		serializer.writeInt(entityId);
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) y);
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		return RecyclableSingletonList.create(serializer);
	}

}
