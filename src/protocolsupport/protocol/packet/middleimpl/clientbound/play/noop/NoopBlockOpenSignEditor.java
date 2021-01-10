package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;

public class NoopBlockOpenSignEditor extends MiddleBlockOpenSignEditor {

	public NoopBlockOpenSignEditor(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
	}

}
