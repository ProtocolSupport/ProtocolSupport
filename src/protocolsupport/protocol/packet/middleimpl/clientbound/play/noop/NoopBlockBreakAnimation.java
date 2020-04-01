package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;

public class NoopBlockBreakAnimation extends MiddleBlockBreakAnimation {

	public NoopBlockBreakAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
