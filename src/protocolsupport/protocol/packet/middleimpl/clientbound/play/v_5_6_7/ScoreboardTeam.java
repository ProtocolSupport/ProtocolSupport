package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.Utils;
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
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArraySerializer.writeShortTArray(serializer, players, (to, element) -> StringSerializer.writeString(to, version, Utils.clampString(element, 16)));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
