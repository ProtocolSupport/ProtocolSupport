package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;

public class NoopEntitySetAttributes extends MiddleEntitySetAttributes {

	public NoopEntitySetAttributes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
	}

}
