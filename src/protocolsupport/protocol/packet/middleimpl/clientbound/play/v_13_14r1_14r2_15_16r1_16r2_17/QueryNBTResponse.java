package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleQueryNBTResponse;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class QueryNBTResponse extends MiddleQueryNBTResponse {

	public QueryNBTResponse(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData querynbtresponse = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_QUERY_NBT_RESPONSE);
		querynbtresponse.writeBytes(data);
		codec.writeClientbound(querynbtresponse);
	}

}
