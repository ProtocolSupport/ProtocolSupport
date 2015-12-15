package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.Utils;

public class ScoreboardTeam extends MiddleScoreboardTeam<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(name);
		serializer.writeByte(mode);
		if (mode == 0 || mode == 2) {
			serializer.writeString(displayName);
			serializer.writeString(prefix);
			serializer.writeString(suffix);
			serializer.writeByte(friendlyFire);
		}
		if (mode == 0 || mode == 3 || mode == 4) {
			serializer.writeShort(players.length);
			for (String player : players) {
				serializer.writeString(Utils.clampString(player, 16));
			}
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, serializer));
	}

}
