package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class InventoryClose extends MiddleInventoryClose {

	public InventoryClose(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData windowclose = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_CLOSE);
		windowclose.writeByte(windowId);
		codec.write(windowclose);
	}

}
