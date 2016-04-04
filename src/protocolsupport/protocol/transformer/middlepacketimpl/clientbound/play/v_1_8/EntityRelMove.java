package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMove extends MiddleEntityRelMove<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeByte(relX / 128);
		serializer.writeByte(relY / 128);
		serializer.writeByte(relZ / 128);
		serializer.writeBoolean(onGround);
		return RecyclableSingletonList.create(serializer);
	}

}
