package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, version);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode);
		if ((mode == 0) || (mode == 2)) {
			StringSerializer.writeString(serializer, version, displayName);
			StringSerializer.writeString(serializer, version, prefix);
			StringSerializer.writeString(serializer, version, suffix);
			serializer.writeByte(friendlyFire);
			StringSerializer.writeString(serializer, version, nameTagVisibility);
			StringSerializer.writeString(serializer, version, collisionRule);
			serializer.writeByte(color);
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			VarNumberSerializer.writeVarInt(serializer, players.length);
			for (String player : players) {
				StringSerializer.writeString(serializer, version, version.isBefore(ProtocolVersion.MINECRAFT_1_9) ? Utils.clampString(player, 16) : player);
			}
		}
		return RecyclableSingletonList.<ClientBoundPacketData>create(serializer);
	}

}
