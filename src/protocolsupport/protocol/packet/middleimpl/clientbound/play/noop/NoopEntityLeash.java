package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;

public class NoopEntityLeash extends MiddleEntityLeash {

	public NoopEntityLeash(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
