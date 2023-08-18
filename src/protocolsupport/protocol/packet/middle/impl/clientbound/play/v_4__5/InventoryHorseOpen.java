package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__5;

import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractStateIdTrackInventoryHorseOpen;

public class InventoryHorseOpen extends AbstractStateIdTrackInventoryHorseOpen implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5
{

	public InventoryHorseOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeServerboundAndFlush(MiddleInventoryClose.create(windowId));
	}

}
