package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode);
		if ((mode == 0) || (mode == 2)) {
			StringSerializer.writeString(serializer, version, ChatAPI.toJSON(displayName));
			serializer.writeByte(friendlyFire);
			StringSerializer.writeString(serializer, version, nameTagVisibility);
			StringSerializer.writeString(serializer, version, collisionRule);
			serializer.writeByte(color);
			StringSerializer.writeString(serializer, version, ChatAPI.toJSON(prefix));
			StringSerializer.writeString(serializer, version, ChatAPI.toJSON(suffix));
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			ArraySerializer.writeVarIntStringArray(serializer, version, players);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
