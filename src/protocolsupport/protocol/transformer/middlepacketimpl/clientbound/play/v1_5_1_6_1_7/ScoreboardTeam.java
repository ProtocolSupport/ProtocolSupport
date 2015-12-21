package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardTeam extends MiddleScoreboardTeam<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, version);
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
		return RecyclableSingletonList.<PacketData>create(serializer);
	}

}
