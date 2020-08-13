package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class InventoryConfirmTransaction extends MiddleInventoryConfirmTransaction {

	public InventoryConfirmTransaction(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_TRANSACTION);
		serializer.writeByte(windowId);
		serializer.writeShort(actionNumber);
		serializer.writeBoolean(accepted);
		codec.write(serializer);
	}

}
