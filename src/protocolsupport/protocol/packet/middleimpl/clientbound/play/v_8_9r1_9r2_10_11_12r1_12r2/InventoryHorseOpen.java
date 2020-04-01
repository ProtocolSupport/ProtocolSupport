package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class InventoryHorseOpen extends MiddleInventoryHorseOpen {

	public InventoryHorseOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData windowhorseopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(windowhorseopen, windowId, "EntityHorse", "Horse", slots);
		windowhorseopen.writeInt(entityId);
		codec.write(windowhorseopen);
	}

}
