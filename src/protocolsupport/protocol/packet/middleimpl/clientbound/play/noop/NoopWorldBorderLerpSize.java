package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderLerpSize;

public class NoopWorldBorderLerpSize extends MiddleWorldBorderLerpSize {

	public NoopWorldBorderLerpSize(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
