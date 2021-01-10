package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareTags;

public class NoopDeclareTags extends MiddleDeclareTags {

	public NoopDeclareTags(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
