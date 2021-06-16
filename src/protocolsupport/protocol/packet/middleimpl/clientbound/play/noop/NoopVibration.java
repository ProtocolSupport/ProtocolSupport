package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVibration;

public class NoopVibration extends MiddleVibration {

	protected NoopVibration(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
