package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAttributes;

public class NoopEntityAttributes extends MiddleEntityAttributes {

	public NoopEntityAttributes(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
