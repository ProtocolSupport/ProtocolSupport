package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareCommands;

public class NoopDeclareCommands extends MiddleDeclareCommands {

	public NoopDeclareCommands(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
