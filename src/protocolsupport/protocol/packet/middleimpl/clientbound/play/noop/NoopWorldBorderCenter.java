package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderCenter;

public class NoopWorldBorderCenter extends MiddleWorldBorderCenter {

	public NoopWorldBorderCenter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
