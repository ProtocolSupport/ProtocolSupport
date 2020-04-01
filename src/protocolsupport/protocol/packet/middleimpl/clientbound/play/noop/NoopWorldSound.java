package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;

public class NoopWorldSound extends MiddleWorldSound {

	public NoopWorldSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
