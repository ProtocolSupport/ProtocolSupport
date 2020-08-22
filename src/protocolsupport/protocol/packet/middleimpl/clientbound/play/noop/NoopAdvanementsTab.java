package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancementsTab;

public class NoopAdvanementsTab extends MiddleAdvancementsTab {

	public NoopAdvanementsTab(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
	}

}
