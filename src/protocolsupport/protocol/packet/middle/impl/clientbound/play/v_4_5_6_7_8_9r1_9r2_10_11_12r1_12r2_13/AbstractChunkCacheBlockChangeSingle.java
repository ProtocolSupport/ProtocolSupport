package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLimitedHeightBlockChangeSingle;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class AbstractChunkCacheBlockChangeSingle extends AbstractLimitedHeightBlockChangeSingle {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheBlockChangeSingle(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();

		int x = position.getX();
		int z = position.getZ();

		LimitedHeightCachedChunk cachedChunk = chunkCache.get(ChunkCoord.fromGlobal(x, z));

		if (cachedChunk == null) {
			throw MiddlePacketCancelException.INSTANCE;
		}

		int y = position.getY();
		cachedChunk.setBlock(y >> 4, LimitedHeightCachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF), (short) blockdata);
	}

}