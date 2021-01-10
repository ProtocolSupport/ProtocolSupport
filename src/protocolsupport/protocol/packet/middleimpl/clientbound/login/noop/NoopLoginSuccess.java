package protocolsupport.protocol.packet.middleimpl.clientbound.login.noop;

import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;

public class NoopLoginSuccess extends MiddleLoginSuccess {

	public NoopLoginSuccess(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
