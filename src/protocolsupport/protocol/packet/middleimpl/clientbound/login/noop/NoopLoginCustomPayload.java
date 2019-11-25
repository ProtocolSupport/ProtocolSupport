package protocolsupport.protocol.packet.middleimpl.clientbound.login.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginCustomPayload;

public class NoopLoginCustomPayload extends MiddleLoginCustomPayload {

	public NoopLoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
