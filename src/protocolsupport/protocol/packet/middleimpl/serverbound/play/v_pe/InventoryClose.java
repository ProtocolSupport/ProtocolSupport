package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
	}

}
