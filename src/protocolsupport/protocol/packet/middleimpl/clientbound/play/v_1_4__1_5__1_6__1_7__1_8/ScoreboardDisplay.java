package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardDisplay;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardDisplay extends MiddleScoreboardDisplay<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, version);
		serializer.writeByte(position);
		serializer.writeString(name);
		return RecyclableSingletonList.create(serializer);
	}

}
