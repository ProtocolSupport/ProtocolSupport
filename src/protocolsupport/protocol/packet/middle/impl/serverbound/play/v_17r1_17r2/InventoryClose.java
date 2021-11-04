package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;

public class InventoryClose extends MiddleInventoryClose implements
IServerboundMiddlePacketV17r1,
IServerboundMiddlePacketV17r2 {

	public InventoryClose(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		windowId = clientdata.readByte();
	}

}
