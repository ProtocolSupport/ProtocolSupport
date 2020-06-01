package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public ServerBoundMiddlePacket(ConnectionImpl connection) {
		super(connection);
	}

	public void decode(ByteBuf clientdata) {
		readFromClientData(clientdata);
		writeToServer();
	}

	protected abstract void readFromClientData(ByteBuf clientdata);

	protected abstract void writeToServer();

}
