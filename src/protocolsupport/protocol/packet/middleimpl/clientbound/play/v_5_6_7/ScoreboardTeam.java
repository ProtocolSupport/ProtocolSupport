package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.AbstractScoreboardTeam;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.utils.Utils;

public class ScoreboardTeam extends AbstractScoreboardTeam {

	public ScoreboardTeam(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData scoreboardteam = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_TEAM);
		StringSerializer.writeString(scoreboardteam, version, name);
		MiscSerializer.writeByteEnum(scoreboardteam, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			StringSerializer.writeString(scoreboardteam, version, LegacyChat.clampLegacyText(displayName.toLegacyText(clientCache.getLocale()), 32));
			Any<String, String> nfix = formatPrefixSuffix();
			StringSerializer.writeString(scoreboardteam, version, nfix.getObj1());
			StringSerializer.writeString(scoreboardteam, version, nfix.getObj2());
			scoreboardteam.writeByte(friendlyFire);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArraySerializer.writeShortTArray(scoreboardteam, players, (to, element) -> StringSerializer.writeString(to, version, Utils.clampString(element, 16)));
		}
		codec.write(scoreboardteam);
	}

}
