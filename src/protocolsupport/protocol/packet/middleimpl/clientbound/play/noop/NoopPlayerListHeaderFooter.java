package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListHeaderFooter;

public class NoopPlayerListHeaderFooter extends MiddlePlayerListHeaderFooter {

	public NoopPlayerListHeaderFooter(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
	}

}
