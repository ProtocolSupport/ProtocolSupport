package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;

public class NoopPlayerAbilities extends MiddlePlayerAbilities {

	public NoopPlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
