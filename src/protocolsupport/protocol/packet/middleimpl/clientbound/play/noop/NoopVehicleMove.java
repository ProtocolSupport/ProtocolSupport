package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehicleMove;

public class NoopVehicleMove extends MiddleVehicleMove {

	public NoopVehicleMove(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
	}

}
