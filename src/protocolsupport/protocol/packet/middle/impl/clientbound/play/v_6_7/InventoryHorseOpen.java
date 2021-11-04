package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractStateIdTrackInventoryHorseOpen;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5_6_7.InventoryOpen;

public class InventoryHorseOpen extends AbstractStateIdTrackInventoryHorseOpen implements
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public InventoryHorseOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData windowhorseopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(windowhorseopen, version, windowId, 11, "Horse", slots);
		windowhorseopen.writeInt(entityId);
		io.writeClientbound(windowhorseopen);
	}

}
