package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardDisplay;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class ScoreboardDisplay extends MiddleScoreboardDisplay {

	public ScoreboardDisplay(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData scoreboarddisplay = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_DISPLAY_SLOT);
		scoreboarddisplay.writeByte(position);
		StringSerializer.writeString(scoreboarddisplay, version, name);
		codec.write(scoreboarddisplay);
	}

}
