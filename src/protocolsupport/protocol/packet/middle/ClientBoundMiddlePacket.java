package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public ClientBoundMiddlePacket(ConnectionImpl connection) {
		super(connection);
	}

	public abstract void readFromServerData(ByteBuf serverdata);

	public boolean postFromServerRead() {
		return true;
	}

	public abstract void writeToClient();

	public void postHandle() {
	}

}
