package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2.AbstractCachedHeldSlot;

public class HeldSlot extends AbstractCachedHeldSlot {

	public HeldSlot(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData heldslot = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_HELD_SLOT);
		heldslot.writeByte(slot);
		codec.writeClientbound(heldslot);
	}

}
