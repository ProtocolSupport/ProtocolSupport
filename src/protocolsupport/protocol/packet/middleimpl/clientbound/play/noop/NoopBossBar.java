package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBossBar;

public class NoopBossBar extends MiddleBossBar {

	public NoopBossBar(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
