package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderWarnDistance;

public class NoopWorldBorderWarnDistance extends MiddleWorldBorderWarnDistance {

	public NoopWorldBorderWarnDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
