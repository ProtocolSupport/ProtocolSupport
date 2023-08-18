package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5__7;

import protocolsupport.api.utils.Any;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__12r2.AbstractScoreboardTeam;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.utils.MiscUtils;

public class ScoreboardTeam extends AbstractScoreboardTeam implements
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public ScoreboardTeam(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardteam = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_TEAM);
		StringCodec.writeString(scoreboardteam, version, name);
		MiscDataCodec.writeByteEnum(scoreboardteam, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			StringCodec.writeString(scoreboardteam, version, LegacyChat.clampLegacyText(displayName.toLegacyText(clientCache.getLocale()), 32));
			Any<String, String> nfix = formatPrefixSuffix();
			StringCodec.writeString(scoreboardteam, version, nfix.getObj1());
			StringCodec.writeString(scoreboardteam, version, nfix.getObj2());
			scoreboardteam.writeByte(friendlyFire);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArrayCodec.writeShortTArray(scoreboardteam, players, (to, element) -> StringCodec.writeString(to, version, MiscUtils.clampString(element, 16)));
		}
		io.writeClientbound(scoreboardteam);
	}

}
