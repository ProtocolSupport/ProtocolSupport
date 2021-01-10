package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;

public class NoopScoreboardTeam extends MiddleScoreboardTeam {

	public NoopScoreboardTeam(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
