package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeBoolean(onGround);
		return RecyclableSingletonList.create(serializer);
	}

}
