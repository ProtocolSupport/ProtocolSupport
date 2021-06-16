package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleAnimation;

public class NoopTitleAnimation extends MiddleTitleAnimation {

	public NoopTitleAnimation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
