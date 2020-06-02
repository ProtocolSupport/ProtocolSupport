package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class InventoryClose extends MiddleInventoryClose {

	public InventoryClose(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData windowclose = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_CLOSE);
		windowclose.writeByte(windowId);
		codec.write(windowclose);
	}

}
