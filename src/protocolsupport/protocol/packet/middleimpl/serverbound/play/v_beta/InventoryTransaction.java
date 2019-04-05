package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;

public class InventoryTransaction extends MiddleInventoryTransaction {

	public InventoryTransaction(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		actionNumber = clientdata.readShort();
		accepted = clientdata.readBoolean();
	}

}
