package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;

public class InventoryClose extends MiddleInventoryClose {

	public InventoryClose(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		windowId = clientdata.readByte();
	}

}
