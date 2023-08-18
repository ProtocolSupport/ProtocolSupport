package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractLimitedHeightBlockChangeMulti;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class AbstractChunkCacheBlockChangeMulti extends AbstractLimitedHeightBlockChangeMulti {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheBlockChangeMulti(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();

		LimitedHeightCachedChunk cachedChunk = chunkCache.get(new ChunkCoord(chunkX, chunkZ));

		if (cachedChunk == null) {
			throw MiddlePacketCancelException.INSTANCE;
		}

		for (BlockChangeRecord record : records) {
			cachedChunk.setBlock(chunkSection, LimitedHeightCachedChunk.getBlockIndex(record.getRelX(), record.getRelY(), record.getRelZ()), (short) record.getBlockData());
		}
	}

}
