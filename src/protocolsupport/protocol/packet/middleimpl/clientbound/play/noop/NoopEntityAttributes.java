package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAttributes;

public class NoopEntityAttributes extends MiddleEntityAttributes {

	public NoopEntityAttributes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
	}

}
