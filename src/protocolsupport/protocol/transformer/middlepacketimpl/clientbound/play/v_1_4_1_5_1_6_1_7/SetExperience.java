package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class SetExperience extends MiddleSetExperience<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeFloat(exp);
		serializer.writeShort(level);
		serializer.writeShort(totalExp);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_EXPERIENCE_ID, serializer));
	}

}
