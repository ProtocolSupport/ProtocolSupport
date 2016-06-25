package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_5__1_6__1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.PacketData;
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
