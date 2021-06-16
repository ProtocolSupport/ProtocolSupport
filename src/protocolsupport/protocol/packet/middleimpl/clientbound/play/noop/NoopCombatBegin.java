package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatBegin;

public class NoopCombatBegin extends MiddleCombatBegin {

	public NoopCombatBegin(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
