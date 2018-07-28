package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleQueryNBTResponse;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class QueryNBTResponse extends MiddleQueryNBTResponse {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_QUERY_NBT_RESPONSE);
		serializer.writeBytes(data);
		return RecyclableSingletonList.create(serializer);
	}

}
