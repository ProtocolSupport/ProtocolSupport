package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStopSound;

public class NoopStopSound extends MiddleStopSound {

	public NoopStopSound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
