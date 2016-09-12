package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityVelocity extends MiddleEntityVelocity<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeShort(motX);
		serializer.writeShort(motY);
		serializer.writeShort(motZ);
		return RecyclableSingletonList.create(serializer);
	}

}
