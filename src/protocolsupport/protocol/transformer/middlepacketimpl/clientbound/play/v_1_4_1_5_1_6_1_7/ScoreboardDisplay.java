package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleScoreboardDisplay;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class ScoreboardDisplay extends MiddleScoreboardDisplay<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(position);
		serializer.writeString(name);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, serializer));
	}

}
