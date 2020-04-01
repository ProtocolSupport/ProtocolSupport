package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class HeldSlot extends MiddleHeldSlot {

	public HeldSlot(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData heldslot = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_HELD_SLOT);
		heldslot.writeShort(slot);
		codec.write(heldslot);
	}

}
