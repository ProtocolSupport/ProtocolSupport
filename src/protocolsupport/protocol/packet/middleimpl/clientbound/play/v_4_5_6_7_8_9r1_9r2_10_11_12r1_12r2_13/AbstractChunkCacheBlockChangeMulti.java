package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class AbstractChunkCacheBlockChangeMulti extends MiddleBlockChangeMulti {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheBlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		LimitedHeightCachedChunk cachedChunk = chunkCache.get(new ChunkCoord(getChunkX(chunkCoordWithSection), getChunkZ(chunkCoordWithSection)));

		if (cachedChunk == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		int sectionY = getChunkSectionY(chunkCoordWithSection);
		for (long record : records) {
			cachedChunk.setBlock(sectionY, LimitedHeightCachedChunk.getBlockIndex(getRecordRelX(record), getRecordRelY(record), getRecordRelZ(record)), (short) getRecordBlockData(record));
		}
	}

}
