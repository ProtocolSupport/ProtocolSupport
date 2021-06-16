package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleActionbar;

public class NoopActionbar extends MiddleActionbar {

	public NoopActionbar(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
