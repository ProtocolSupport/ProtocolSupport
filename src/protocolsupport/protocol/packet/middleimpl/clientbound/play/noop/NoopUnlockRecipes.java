package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;

public class NoopUnlockRecipes extends MiddleUnlockRecipes {

	public NoopUnlockRecipes(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
