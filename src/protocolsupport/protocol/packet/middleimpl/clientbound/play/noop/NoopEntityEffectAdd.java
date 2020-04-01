package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;

public class NoopEntityEffectAdd extends MiddleEntityEffectAdd {

	public NoopEntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
