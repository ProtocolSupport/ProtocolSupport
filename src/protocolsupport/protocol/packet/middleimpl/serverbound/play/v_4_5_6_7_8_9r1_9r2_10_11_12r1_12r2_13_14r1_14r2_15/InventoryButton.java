package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryButton;

public class InventoryButton extends MiddleInventoryButton {

	public InventoryButton(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		button = clientdata.readUnsignedByte();
	}

}
