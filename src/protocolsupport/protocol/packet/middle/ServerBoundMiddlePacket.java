package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public ServerBoundMiddlePacket(ConnectionImpl connection) {
		super(connection);
	}

	public abstract void readFromClientData(ByteBuf clientdata);

	public abstract void writeToServer();

}
