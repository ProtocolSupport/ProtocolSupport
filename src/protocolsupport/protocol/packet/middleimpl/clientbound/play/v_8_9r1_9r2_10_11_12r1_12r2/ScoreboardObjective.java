package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

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
		StringSerializer.writeVarIntUTF8String(scoreboardobjective, name);
		MiscSerializer.writeByteEnum(scoreboardobjective, mode);
		if (mode != Mode.REMOVE) {
			StringSerializer.writeVarIntUTF8String(scoreboardobjective, LegacyChat.clampLegacyText(value.toLegacyText(cache.getAttributesCache().getLocale()), 32));
			StringSerializer.writeVarIntUTF8String(scoreboardobjective, getTypeString(type));
		}
		codec.write(scoreboardobjective);
	}

	protected static String getTypeString(Type type) {
		switch (type) {
			case HEARTS: {
				return "hearts";
			}
			case INTEGER:
			default: {
				return "integer";
			}
		}
	}

}
