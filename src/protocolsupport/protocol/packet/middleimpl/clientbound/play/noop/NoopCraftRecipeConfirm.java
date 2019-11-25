package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCraftRecipeConfirm;

public class NoopCraftRecipeConfirm extends MiddleCraftRecipeConfirm {

	public NoopCraftRecipeConfirm(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
