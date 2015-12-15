package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class ScoreboardObjective extends MiddleScoreboardObjective<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(name);
		serializer.writeString(mode == 1 ? "" : value);
		serializer.writeByte(mode);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, serializer));
	}

}
