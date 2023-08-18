package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkUnload;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;

public abstract class AbstractChunkCacheChunkUnload extends MiddleChunkUnload {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheChunkUnload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		chunkCache.remove(chunk);
	}

}
