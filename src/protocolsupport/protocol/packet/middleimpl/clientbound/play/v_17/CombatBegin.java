package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatBegin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class CombatBegin extends MiddleCombatBegin {

	public CombatBegin(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatstartPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_COMBAT_BEGIN);
		codec.writeClientbound(combatstartPacket);
	}

}
