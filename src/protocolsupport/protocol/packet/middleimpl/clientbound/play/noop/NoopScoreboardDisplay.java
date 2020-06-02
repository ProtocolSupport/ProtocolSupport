package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardDisplay;

public class NoopScoreboardDisplay extends MiddleScoreboardDisplay {

	public NoopScoreboardDisplay(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
	}

}
