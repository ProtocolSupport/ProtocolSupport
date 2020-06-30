package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE);
		StringSerializer.writeString(scoreboardobjective, version, name);
		StringSerializer.writeString(
			scoreboardobjective, version,
			mode == Mode.REMOVE ? "" : LegacyChat.clampLegacyText(value.toLegacyText(cache.getClientCache().getLocale()), 32)
		);
		MiscSerializer.writeByteEnum(scoreboardobjective, mode);
		codec.write(scoreboardobjective);
	}

}
