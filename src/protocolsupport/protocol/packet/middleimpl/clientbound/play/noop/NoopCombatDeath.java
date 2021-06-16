package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatDeath;

public class NoopCombatDeath extends MiddleCombatDeath {

	public NoopCombatDeath(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
