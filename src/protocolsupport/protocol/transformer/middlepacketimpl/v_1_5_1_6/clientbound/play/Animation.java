package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleAnimation;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class Animation extends MiddleAnimation<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeByte(animation + 1);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ANIMATION_ID, serializer));
	}

}
