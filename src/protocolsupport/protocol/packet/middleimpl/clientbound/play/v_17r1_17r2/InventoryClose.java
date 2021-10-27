package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class InventoryClose extends MiddleInventoryClose {

	public InventoryClose(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData windowclose = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_CLOSE);
		windowclose.writeByte(windowId);
		codec.writeClientbound(windowclose);
	}

}
