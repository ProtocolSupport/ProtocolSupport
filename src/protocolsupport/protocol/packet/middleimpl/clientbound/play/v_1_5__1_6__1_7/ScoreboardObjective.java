package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_5__1_6__1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardObjective extends MiddleScoreboardObjective<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, version);
		serializer.writeString(name);
		serializer.writeString(mode == 1 ? "" : value);
		serializer.writeByte(mode);
		return RecyclableSingletonList.create(serializer);
	}

}
