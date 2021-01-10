package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardScore;

public class NoopScoreboardScore extends MiddleScoreboardScore {

	public NoopScoreboardScore(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
