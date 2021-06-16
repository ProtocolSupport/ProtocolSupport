package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleClear;

public class NoopTitleClear extends MiddleTitleClear {

	public NoopTitleClear(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
