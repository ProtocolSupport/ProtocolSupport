package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__6;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractCachedHeldSlot;

public class HeldSlot extends AbstractCachedHeldSlot implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public HeldSlot(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData heldslot = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_HELD_SLOT);
		heldslot.writeShort(slot);
		io.writeClientbound(heldslot);
	}

}
