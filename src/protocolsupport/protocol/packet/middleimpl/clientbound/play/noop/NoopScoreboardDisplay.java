package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardDisplay;

public class NoopScoreboardDisplay extends MiddleScoreboardDisplay {

	public NoopScoreboardDisplay(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
