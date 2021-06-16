package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.AbstractScoreboardTeam;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class ScoreboardTeam extends AbstractScoreboardTeam {

	public ScoreboardTeam(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardteam = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_TEAM);
		StringSerializer.writeVarIntUTF8String(scoreboardteam, name);
		MiscSerializer.writeByteEnum(scoreboardteam, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			StringSerializer.writeVarIntUTF8String(scoreboardteam, LegacyChat.clampLegacyText(displayName.toLegacyText(clientCache.getLocale()), 32));
			Any<String, String> nfix = formatPrefixSuffix();
			StringSerializer.writeVarIntUTF8String(scoreboardteam, nfix.getObj1());
			StringSerializer.writeVarIntUTF8String(scoreboardteam, nfix.getObj2());
			scoreboardteam.writeByte(friendlyFire);
			StringSerializer.writeVarIntUTF8String(scoreboardteam, nameTagVisibility);
			StringSerializer.writeVarIntUTF8String(scoreboardteam, collisionRule);
			scoreboardteam.writeByte(format.isColor() ? format.ordinal() : -1);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArraySerializer.writeVarIntVarIntUTF8StringArray(scoreboardteam, players);
		}
		codec.writeClientbound(scoreboardteam);
	}

}
