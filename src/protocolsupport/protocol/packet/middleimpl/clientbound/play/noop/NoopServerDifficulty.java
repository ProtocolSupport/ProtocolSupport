package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleServerDifficulty;

public class NoopServerDifficulty extends MiddleServerDifficulty {

	public NoopServerDifficulty(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
