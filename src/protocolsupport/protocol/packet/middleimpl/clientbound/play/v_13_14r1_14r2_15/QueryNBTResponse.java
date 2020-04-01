package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleQueryNBTResponse;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class QueryNBTResponse extends MiddleQueryNBTResponse {

	public QueryNBTResponse(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData querynbtresponse = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_QUERY_NBT_RESPONSE);
		querynbtresponse.writeBytes(data);
		codec.write(querynbtresponse);
	}

}
