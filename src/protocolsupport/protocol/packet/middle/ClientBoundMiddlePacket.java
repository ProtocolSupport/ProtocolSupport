package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public ClientBoundMiddlePacket(MiddlePacketInit init) {
		super(init);
	}

	public void encode(ByteBuf serverdata) {
		encode0(serverdata);
		cleanup();
	}

	private void encode0(ByteBuf serverdata) {
		try {
			readServerData(serverdata);
			handleReadData();
		} catch (CancelMiddlePacketException e) {
			return;
		}
		writeToClient();
	}

	protected abstract void readServerData(ByteBuf serverdata);

	protected void handleReadData() {
	}

	protected abstract void writeToClient();

	protected void cleanup() {
	}

}
