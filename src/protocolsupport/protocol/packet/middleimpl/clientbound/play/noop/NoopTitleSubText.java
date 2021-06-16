package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleSubText;

public class NoopTitleSubText extends MiddleTitleSubText {

	public NoopTitleSubText(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
