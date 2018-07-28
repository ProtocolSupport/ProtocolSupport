package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryEnchant;

public class InventoryEnchant extends MiddleInventoryEnchant {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		enchantment = clientdata.readUnsignedByte();
	}

}
