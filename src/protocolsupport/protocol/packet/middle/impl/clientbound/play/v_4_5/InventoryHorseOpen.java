package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5;

import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractStateIdTrackInventoryHorseOpen;

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
