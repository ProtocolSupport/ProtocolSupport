package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleText;

public class NoopTitleText extends MiddleTitleText {

	public NoopTitleText(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
