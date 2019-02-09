package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	public ScoreboardTeam(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID);
		StringSerializer.writeString(serializer, version, name);
		MiscSerializer.writeByteEnum(serializer, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			String locale = cache.getAttributesCache().getLocale();
			StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(displayName.toLegacyText(locale), 32));
			StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(prefix.toLegacyText(locale), 16));
			StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(suffix.toLegacyText(locale), 16));
			serializer.writeByte(friendlyFire);
			StringSerializer.writeString(serializer, version, nameTagVisibility);
			StringSerializer.writeString(serializer, version, collisionRule);
			serializer.writeByte(color <= 15 ? color : -1);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArraySerializer.writeVarIntStringArray(serializer, version, players);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
