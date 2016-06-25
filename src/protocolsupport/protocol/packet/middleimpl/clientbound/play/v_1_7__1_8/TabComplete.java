package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class TabComplete extends MiddleTabComplete<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_TAB_COMPLETE_ID, version);
		serializer.writeVarInt(matches.length);
		for (String match : matches) {
			serializer.writeString(match);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
