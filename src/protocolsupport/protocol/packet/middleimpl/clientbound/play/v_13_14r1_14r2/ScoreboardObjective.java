package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE);
		StringSerializer.writeVarIntUTF8String(scoreboardobjective, name);
		scoreboardobjective.writeByte(mode.ordinal());
		if (mode != Mode.REMOVE) {
			StringSerializer.writeVarIntUTF8String(scoreboardobjective, ChatAPI.toJSON(value));
			MiscSerializer.writeVarIntEnum(scoreboardobjective, type);
		}
		codec.write(scoreboardobjective);
	}

}
