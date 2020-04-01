package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;

public class NoopHeldSlot extends MiddleHeldSlot {

	public NoopHeldSlot(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
