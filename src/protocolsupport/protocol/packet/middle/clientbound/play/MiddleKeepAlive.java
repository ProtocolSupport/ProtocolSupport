package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.KeepAliveCache;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	protected final KeepAliveCache keepaliveCache = cache.getKeepAliveCache();

	public MiddleKeepAlive(MiddlePacketInit init) {
		super(init);
	}

	protected int keepAliveId;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		keepAliveId = keepaliveCache.storeServerKeepAliveId(serverdata.readLong());
	}

}
