package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddlePong;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class Pong extends MiddlePong {

	public Pong(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData pong = ClientBoundPacketData.create(ClientBoundPacketType.STATUS_PONG);
		pong.writeLong(pingId);
		codec.writeClientbound(pong);
	}

}
