package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;

public class NoopUnlockRecipes extends MiddleUnlockRecipes {

	public NoopUnlockRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
