package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ScoreboardScore extends MiddleScoreboardScore {

	public ScoreboardScore(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData scoreboardscore = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_SCORE);
		StringSerializer.writeVarIntUTF8String(scoreboardscore, name);
		scoreboardscore.writeByte(mode);
		StringSerializer.writeVarIntUTF8String(scoreboardscore, objectiveName);
		if (mode != 1) {
			VarNumberSerializer.writeVarInt(scoreboardscore, value);
		}
		codec.write(scoreboardscore);
	}

}
