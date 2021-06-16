package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEnd;

public class NoopCombatEnd extends MiddleCombatEnd {

	public NoopCombatEnd(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
