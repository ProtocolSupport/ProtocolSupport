package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListHeaderFooter;

public class NoopPlayerListHeaderFooter extends MiddlePlayerListHeaderFooter {

	public NoopPlayerListHeaderFooter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
