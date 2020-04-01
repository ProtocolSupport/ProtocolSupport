package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityHeadRotation;

public class NoopEntityHeadRotation extends MiddleEntityHeadRotation {

	public NoopEntityHeadRotation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
