package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;

public class NoopResourcePack extends MiddleResourcePack {

	public NoopResourcePack(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
