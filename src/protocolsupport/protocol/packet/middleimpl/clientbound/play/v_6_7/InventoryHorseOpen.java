package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7.InventoryOpen;

public class InventoryHorseOpen extends MiddleInventoryHorseOpen {

	public InventoryHorseOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData windowhorseopen = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(windowhorseopen, version, windowId, 11, "Horse", slots);
		windowhorseopen.writeInt(entityId);
		codec.writeClientbound(windowhorseopen);
	}

}
