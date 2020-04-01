package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;

public class NoopTabComplete extends MiddleTabComplete {

	public NoopTabComplete(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
