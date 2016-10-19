package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardScore extends MiddleScoreboardScore<RecyclableCollection<ClientBoundPacketData>>  {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) throws IOException {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, version);
		serializer.writeString(name);
		serializer.writeByte(mode);
		serializer.writeString(objectiveName);
		if (mode != 1) {
			serializer.writeVarInt(value);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
