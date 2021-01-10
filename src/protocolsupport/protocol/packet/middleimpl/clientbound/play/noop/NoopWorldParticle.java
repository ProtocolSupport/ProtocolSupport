package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;

public class NoopWorldParticle extends MiddleWorldParticle {

	public NoopWorldParticle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
