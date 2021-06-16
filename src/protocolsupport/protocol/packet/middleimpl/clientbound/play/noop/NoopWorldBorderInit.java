package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderInit;

public class NoopWorldBorderInit extends MiddleWorldBorderInit {

	public NoopWorldBorderInit(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
