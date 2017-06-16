package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardScore extends MiddleScoreboardScore {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, version);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode);
		StringSerializer.writeString(serializer, version, objectiveName);
		if (mode != 1) {
			VarNumberSerializer.writeVarInt(serializer, value);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
