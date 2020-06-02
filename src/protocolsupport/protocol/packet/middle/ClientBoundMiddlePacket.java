package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public ClientBoundMiddlePacket(ConnectionImpl connection) {
		super(connection);
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
