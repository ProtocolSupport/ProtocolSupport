package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	public MiddleKeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	protected int keepAliveId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		keepAliveId = cache.getKeepAliveCache().storeServerKeepAliveId(serverdata.readLong());
	}

}
