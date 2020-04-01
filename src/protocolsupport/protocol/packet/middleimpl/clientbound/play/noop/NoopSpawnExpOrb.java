package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;

public class NoopSpawnExpOrb extends MiddleSpawnExpOrb {

	public NoopSpawnExpOrb(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
