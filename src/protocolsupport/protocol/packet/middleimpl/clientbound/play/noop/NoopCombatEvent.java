package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEvent;

public class NoopCombatEvent extends MiddleCombatEvent {

	public NoopCombatEvent(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
