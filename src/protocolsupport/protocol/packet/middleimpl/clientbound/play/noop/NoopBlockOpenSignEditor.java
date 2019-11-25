package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;

public class NoopBlockOpenSignEditor extends MiddleBlockOpenSignEditor {

	public NoopBlockOpenSignEditor(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
	}

}
