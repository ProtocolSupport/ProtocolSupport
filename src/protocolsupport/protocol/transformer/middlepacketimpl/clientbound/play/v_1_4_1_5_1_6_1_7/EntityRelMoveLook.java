package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMoveLook extends MiddleEntityRelMoveLook<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, version);
		serializer.writeInt(entityId);
		serializer.writeByte(relX);
		serializer.writeByte(relY);
		serializer.writeByte(relZ);
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		return RecyclableSingletonList.create(serializer);
	}

}
