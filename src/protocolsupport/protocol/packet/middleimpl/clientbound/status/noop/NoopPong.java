package protocolsupport.protocol.packet.middleimpl.clientbound.status.noop;

import protocolsupport.protocol.packet.middle.clientbound.status.MiddlePong;

public class NoopPong extends MiddlePong {

	public NoopPong(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
