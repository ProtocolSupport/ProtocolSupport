package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	public ScoreboardTeam(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode);
		if ((mode == 0) || (mode == 2)) {
			String locale = cache.getAttributesCache().getLocale();
			StringSerializer.writeString(serializer, version, Utils.clampString(displayName.toLegacyText(locale), 32));
			StringSerializer.writeString(serializer, version, Utils.clampString(prefix.toLegacyText(locale), 16));
			StringSerializer.writeString(serializer, version, Utils.clampString(suffix.toLegacyText(locale), 16));
			serializer.writeByte(friendlyFire);
			StringSerializer.writeString(serializer, version, nameTagVisibility);
			serializer.writeByte(color <= 15 ? color : -1);
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			ArraySerializer.writeVarIntTArray(serializer, players, (to, element) -> StringSerializer.writeString(to, version, Utils.clampString(element, 16)));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
