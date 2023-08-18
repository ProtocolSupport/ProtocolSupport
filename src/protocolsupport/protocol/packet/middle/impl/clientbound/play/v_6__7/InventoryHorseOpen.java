package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6__7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractStateIdTrackInventoryHorseOpen;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5__7.InventoryOpen;

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
