package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderSize;

public class NoopWorldBorderSize extends MiddleWorldBorderSize {

	public NoopWorldBorderSize(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
