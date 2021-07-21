package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.api.utils.Any;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.AbstractScoreboardTeam;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.utils.MiscUtils;

public class ScoreboardTeam extends AbstractScoreboardTeam {

	public ScoreboardTeam(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardteam = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_TEAM);
		StringCodec.writeVarIntUTF8String(scoreboardteam, name);
		MiscDataCodec.writeByteEnum(scoreboardteam, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			StringCodec.writeVarIntUTF8String(scoreboardteam, LegacyChat.clampLegacyText(displayName.toLegacyText(clientCache.getLocale()), 32));
			Any<String, String> nfix = formatPrefixSuffix();
			StringCodec.writeVarIntUTF8String(scoreboardteam, nfix.getObj1());
			StringCodec.writeVarIntUTF8String(scoreboardteam, nfix.getObj2());
			scoreboardteam.writeByte(friendlyFire);
			StringCodec.writeVarIntUTF8String(scoreboardteam, nameTagVisibility);
			scoreboardteam.writeByte(format.isColor() ? format.ordinal() : -1);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArrayCodec.writeVarIntTArray(scoreboardteam, players, (to, element) -> StringCodec.writeVarIntUTF8String(to, MiscUtils.clampString(element, 16)));
		}
		codec.writeClientbound(scoreboardteam);
	}

}
