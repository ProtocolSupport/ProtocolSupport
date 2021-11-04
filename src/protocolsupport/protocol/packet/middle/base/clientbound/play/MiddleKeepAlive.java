package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.KeepAliveCache;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	protected final KeepAliveCache keepaliveCache = cache.getKeepAliveCache();

	protected MiddleKeepAlive(IMiddlePacketInit init) {
		super(init);
	}

	protected int keepAliveId;

	@Override
	protected void decode(ByteBuf serverdata) {
		keepAliveId = keepaliveCache.storeServerKeepAliveId(serverdata.readLong());
	}

}
