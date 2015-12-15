package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityHeadRotation;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class EntityHeadRotation extends MiddleEntityHeadRotation<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeByte(headRot);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, serializer));
	}

}
