package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateViewDistance;

public class NoopUpdateViewDistance extends MiddleUpdateViewDistance {

	public NoopUpdateViewDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
