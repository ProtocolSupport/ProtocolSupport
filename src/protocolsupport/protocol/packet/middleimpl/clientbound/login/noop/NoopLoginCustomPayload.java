package protocolsupport.protocol.packet.middleimpl.clientbound.login.noop;

import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginCustomPayload;

public class NoopLoginCustomPayload extends MiddleLoginCustomPayload {

	public NoopLoginCustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
