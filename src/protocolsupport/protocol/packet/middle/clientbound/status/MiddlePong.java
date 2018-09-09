package protocolsupport.protocol.packet.middle.clientbound.status;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddlePong extends ClientBoundMiddlePacket {

	public MiddlePong(ConnectionImpl connection) {
		super(connection);
	}

	protected long pingId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		pingId = serverdata.readLong();
	}

}
