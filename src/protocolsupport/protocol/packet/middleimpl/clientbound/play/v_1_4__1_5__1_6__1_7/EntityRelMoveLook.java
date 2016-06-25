package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMoveLook extends MiddleEntityRelMoveLook<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, version);
		serializer.writeInt(entityId);
		serializer.writeByte(relX / 128);
		serializer.writeByte(relY / 128);
		serializer.writeByte(relZ / 128);
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		return RecyclableSingletonList.create(serializer);
	}

}
