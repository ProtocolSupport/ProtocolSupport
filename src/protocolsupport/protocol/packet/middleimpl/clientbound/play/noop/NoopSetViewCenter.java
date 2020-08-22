package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetViewCenter;

public class NoopSetViewCenter extends MiddleSetViewCenter {

	public NoopSetViewCenter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
