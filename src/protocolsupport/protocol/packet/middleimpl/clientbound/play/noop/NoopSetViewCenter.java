package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetViewCenter;

public class NoopSetViewCenter extends MiddleSetViewCenter {

	public NoopSetViewCenter(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
	}

}
