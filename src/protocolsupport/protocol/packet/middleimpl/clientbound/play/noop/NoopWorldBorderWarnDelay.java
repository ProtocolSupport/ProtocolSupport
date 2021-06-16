package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderWarnDelay;

public class NoopWorldBorderWarnDelay extends MiddleWorldBorderWarnDelay {

	public NoopWorldBorderWarnDelay(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
