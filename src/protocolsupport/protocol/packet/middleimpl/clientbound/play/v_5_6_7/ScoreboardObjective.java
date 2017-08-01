package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, version);
		StringSerializer.writeString(serializer, version, name);
		StringSerializer.writeString(serializer, version, mode == 1 ? "" : value);
		serializer.writeByte(mode);
		return RecyclableSingletonList.create(serializer);
	}

}
