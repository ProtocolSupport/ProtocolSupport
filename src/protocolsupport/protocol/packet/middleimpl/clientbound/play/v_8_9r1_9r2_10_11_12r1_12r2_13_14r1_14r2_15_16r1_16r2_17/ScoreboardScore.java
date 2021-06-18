package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class ScoreboardScore extends MiddleScoreboardScore {

	public ScoreboardScore(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardscore = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_SCORE);
		StringCodec.writeVarIntUTF8String(scoreboardscore, name);
		scoreboardscore.writeByte(mode);
		StringCodec.writeVarIntUTF8String(scoreboardscore, objectiveName);
		if (mode != 1) {
			VarNumberCodec.writeVarInt(scoreboardscore, value);
		}
		codec.writeClientbound(scoreboardscore);
	}

}
