package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.ConnectionImpl;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public ClientBoundMiddlePacket(ConnectionImpl connection) {
		super(connection);
	}

	public void encode(ByteBuf serverdata) {
		readFromServerData(serverdata);
		if (serverdata.isReadable()) {
			throw new DecoderException("Data not read fully, bytes left " + serverdata.readableBytes());
		}
		if (postFromServerRead()) {
			writeToClient();
		}
		postHandle();
	}

	protected abstract void readFromServerData(ByteBuf serverdata);

	protected boolean postFromServerRead() {
		return true;
	}

	protected abstract void writeToClient();

	protected void postHandle() {
	}

}
