package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	protected int keepAliveId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		keepAliveId = cache.storeServerKeepAliveId(serverdata.readLong());
	}

}
