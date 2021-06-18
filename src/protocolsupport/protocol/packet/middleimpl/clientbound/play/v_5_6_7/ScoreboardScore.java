package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.Utils;

public class ScoreboardScore extends MiddleScoreboardScore {

	public ScoreboardScore(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardscore = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_SCORE);
		StringCodec.writeString(scoreboardscore, version, Utils.clampString(name, 16));
		scoreboardscore.writeByte(mode);
		if (mode != 1) {
			StringCodec.writeString(scoreboardscore, version, objectiveName);
			scoreboardscore.writeInt(value);
		}
		codec.writeClientbound(scoreboardscore);
	}

}
