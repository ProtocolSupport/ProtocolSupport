package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, version);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode);
		if ((mode == 0) || (mode == 2)) {
			StringSerializer.writeString(serializer, version, displayName);
			StringSerializer.writeString(serializer, version, prefix);
			StringSerializer.writeString(serializer, version, suffix);
			serializer.writeByte(friendlyFire);
			StringSerializer.writeString(serializer, version, nameTagVisibility);
			serializer.writeByte(color);
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			ArraySerializer.writeVarIntTArray(serializer, players, (to, element) -> StringSerializer.writeString(to, version, Utils.clampString(element, 16)));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
