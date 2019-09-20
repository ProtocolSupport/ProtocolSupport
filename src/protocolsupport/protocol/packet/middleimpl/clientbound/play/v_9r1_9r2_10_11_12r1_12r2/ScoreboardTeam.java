package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
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
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_TEAM);
		StringSerializer.writeVarIntUTF8String(serializer, name);
		MiscSerializer.writeByteEnum(serializer, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			String locale = cache.getAttributesCache().getLocale();
			StringSerializer.writeVarIntUTF8String(serializer, LegacyChat.clampLegacyText(displayName.toLegacyText(locale), 32));
			StringSerializer.writeVarIntUTF8String(serializer, LegacyChat.clampLegacyText(LegacyChat.addScoreboardTeamColorToPrefix(prefix.toLegacyText(locale), color), 16));
			StringSerializer.writeVarIntUTF8String(serializer, LegacyChat.clampLegacyText(suffix.toLegacyText(locale), 16));
			serializer.writeByte(friendlyFire);
			StringSerializer.writeVarIntUTF8String(serializer, nameTagVisibility);
			StringSerializer.writeVarIntUTF8String(serializer, collisionRule);
			serializer.writeByte(color <= 15 ? color : -1);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArraySerializer.writeVarIntVarIntUTF8StringArray(serializer, players);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
