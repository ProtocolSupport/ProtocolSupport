package protocolsupport.protocol.packet.middleimpl.clientbound.status.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddlePong;

public class NoopPong extends MiddlePong {

	public NoopPong(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
