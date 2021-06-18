package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_OBJECTIVE);
		StringCodec.writeString(scoreboardobjective, version, name);
		StringCodec.writeString(
			scoreboardobjective, version,
			mode == Mode.REMOVE ? "" : LegacyChat.clampLegacyText(value.toLegacyText(cache.getClientCache().getLocale()), 32)
		);
		MiscDataCodec.writeByteEnum(scoreboardobjective, mode);
		codec.writeClientbound(scoreboardobjective);
	}

}
