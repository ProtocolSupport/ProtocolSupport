package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCraftRecipeConfirm;

public class NoopCraftRecipeConfirm extends MiddleCraftRecipeConfirm {

	public NoopCraftRecipeConfirm(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
