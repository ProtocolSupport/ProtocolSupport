package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCamera;

public class NoopCamera extends MiddleCamera {

	public NoopCamera(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
