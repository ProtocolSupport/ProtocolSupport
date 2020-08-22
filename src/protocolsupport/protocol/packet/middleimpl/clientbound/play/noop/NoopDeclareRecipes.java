package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;

public class NoopDeclareRecipes extends MiddleDeclareRecipes {

	public NoopDeclareRecipes(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
