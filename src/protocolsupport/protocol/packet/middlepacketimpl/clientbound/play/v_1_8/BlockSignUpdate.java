package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleBlockSignUpdate;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockSignUpdate extends MiddleBlockSignUpdate<RecyclableCollection<PacketData>>  {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_SIGN_ID, version);
		serializer.writePosition(position);
		for (String lineJson : linesJson) {
			serializer.writeString(lineJson);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
