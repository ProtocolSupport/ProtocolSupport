package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateViewDistance;

public class NoopUpdateViewDistance extends MiddleUpdateViewDistance {

	public NoopUpdateViewDistance(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
