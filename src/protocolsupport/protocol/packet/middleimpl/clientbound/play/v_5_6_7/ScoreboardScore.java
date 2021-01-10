package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;

public class ScoreboardScore extends MiddleScoreboardScore {

	public ScoreboardScore(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardscore = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_SCORE);
		StringSerializer.writeString(scoreboardscore, version, Utils.clampString(name, 16));
		scoreboardscore.writeByte(mode);
		if (mode != 1) {
			StringSerializer.writeString(scoreboardscore, version, objectiveName);
			scoreboardscore.writeInt(value);
		}
		codec.writeClientbound(scoreboardscore);
	}

}
