package protocolsupport.protocol.packet.middleimpl.clientbound.login.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;

public class NoopLoginSuccess extends MiddleLoginSuccess {

	public NoopLoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
