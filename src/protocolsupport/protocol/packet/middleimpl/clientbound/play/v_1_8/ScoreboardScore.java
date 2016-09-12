package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardScore extends MiddleScoreboardScore<RecyclableCollection<PacketData>>  {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, version);
		serializer.writeString(name);
		serializer.writeByte(mode);
		serializer.writeString(objectiveName);
		if (mode != 1) {
			serializer.writeVarInt(value);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
