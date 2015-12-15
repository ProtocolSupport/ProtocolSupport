package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.Utils;

public class ScoreboardScore extends MiddleScoreboardScore<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(Utils.clampString(name, 16));
		serializer.writeByte(mode);
		if (mode != 1) {
			serializer.writeString(objectiveName);
			serializer.writeInt(value);
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, serializer));
	}

}
