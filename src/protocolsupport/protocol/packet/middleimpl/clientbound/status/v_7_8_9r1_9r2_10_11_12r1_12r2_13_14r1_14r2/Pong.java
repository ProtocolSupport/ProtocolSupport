package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddlePong;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class Pong extends MiddlePong {

	public Pong(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData pong = ClientBoundPacketData.create(PacketType.CLIENTBOUND_STATUS_PONG);
		pong.writeLong(pingId);
		codec.write(pong);
	}

}
