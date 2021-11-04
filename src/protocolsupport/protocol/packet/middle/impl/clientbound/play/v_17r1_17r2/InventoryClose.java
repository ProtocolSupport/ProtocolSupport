package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class InventoryClose extends MiddleInventoryClose implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public InventoryClose(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData windowclose = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_CLOSE);
		windowclose.writeByte(windowId);
		io.writeClientbound(windowclose);
	}

}
