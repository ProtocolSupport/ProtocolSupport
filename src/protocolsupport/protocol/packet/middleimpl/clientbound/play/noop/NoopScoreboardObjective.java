package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;

public class NoopScoreboardObjective extends MiddleScoreboardObjective {

	public NoopScoreboardObjective(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
