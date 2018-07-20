package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode.ordinal());
		if (mode != Mode.REMOVE) {
			StringSerializer.writeString(serializer, version, ChatAPI.toJSON(value));
			MiscSerializer.writeVarIntEnum(serializer, type);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
