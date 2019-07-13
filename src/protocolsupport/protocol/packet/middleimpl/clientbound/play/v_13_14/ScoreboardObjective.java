package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
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
