package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE);
		StringSerializer.writeString(scoreboardobjective, version, name);
		StringSerializer.writeString(
			scoreboardobjective, version,
			mode == Mode.REMOVE ? "" : LegacyChat.clampLegacyText(value.toLegacyText(cache.getClientCache().getLocale()), 32)
		);
		MiscSerializer.writeByteEnum(scoreboardobjective, mode);
		codec.writeClientbound(scoreboardobjective);
	}

}
