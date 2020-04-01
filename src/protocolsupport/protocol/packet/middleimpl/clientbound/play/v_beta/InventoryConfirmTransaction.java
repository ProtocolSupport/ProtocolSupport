package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class InventoryConfirmTransaction extends MiddleInventoryConfirmTransaction {

	public InventoryConfirmTransaction(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData windowtransaction = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_TRANSACTION);
		windowtransaction.writeByte(windowId);
		windowtransaction.writeShort(actionNumber);
		windowtransaction.writeBoolean(accepted);
		codec.write(windowtransaction);
	}

}
