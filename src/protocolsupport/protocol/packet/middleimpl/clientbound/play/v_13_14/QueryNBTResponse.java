package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleQueryNBTResponse;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class QueryNBTResponse extends MiddleQueryNBTResponse {

	public QueryNBTResponse(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_QUERY_NBT_RESPONSE);
		serializer.writeBytes(data);
		return RecyclableSingletonList.create(serializer);
	}

}
