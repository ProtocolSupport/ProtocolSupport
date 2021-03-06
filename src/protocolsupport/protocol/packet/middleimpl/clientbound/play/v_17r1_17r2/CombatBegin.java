package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatBegin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class CombatBegin extends MiddleCombatBegin {

	public CombatBegin(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatstartPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_BEGIN);
		codec.writeClientbound(combatstartPacket);
	}

}
