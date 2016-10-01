package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMove extends MiddleEntityRelMove<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeShort(relX);
		serializer.writeShort(relY);
		serializer.writeShort(relZ);
		serializer.writeBoolean(onGround);
		return RecyclableSingletonList.create(serializer);
	}

}
