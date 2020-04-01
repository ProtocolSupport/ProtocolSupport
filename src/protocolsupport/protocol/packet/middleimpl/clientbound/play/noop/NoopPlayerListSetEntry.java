package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;

public class NoopPlayerListSetEntry extends MiddlePlayerListSetEntry {

	public NoopPlayerListSetEntry(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
