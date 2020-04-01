package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.Utils;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	public ScoreboardTeam(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData scoreboardteam = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_TEAM);
		StringSerializer.writeString(scoreboardteam, version, name);
		MiscSerializer.writeByteEnum(scoreboardteam, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			String locale = cache.getAttributesCache().getLocale();
			StringSerializer.writeString(scoreboardteam, version, LegacyChat.clampLegacyText(displayName.toLegacyText(locale), 32));
			StringSerializer.writeString(scoreboardteam, version, LegacyChat.clampLegacyText(prefix.toLegacyText(locale), 16));
			StringSerializer.writeString(scoreboardteam, version, LegacyChat.clampLegacyText(suffix.toLegacyText(locale), 16));
			scoreboardteam.writeByte(friendlyFire);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArraySerializer.writeShortTArray(scoreboardteam, players, (to, element) -> StringSerializer.writeString(to, version, Utils.clampString(element, 16)));
		}
		codec.write(scoreboardteam);
	}

}
