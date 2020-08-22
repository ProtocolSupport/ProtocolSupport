package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;

public class InventoryHorseOpen extends MiddleInventoryHorseOpen {

	public InventoryHorseOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		codec.readAndComplete(MiddleInventoryClose.create(windowId));
	}

}
