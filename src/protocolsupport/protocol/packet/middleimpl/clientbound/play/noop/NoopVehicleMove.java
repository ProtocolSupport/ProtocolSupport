package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehicleMove;

public class NoopVehicleMove extends MiddleVehicleMove {

	public NoopVehicleMove(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
