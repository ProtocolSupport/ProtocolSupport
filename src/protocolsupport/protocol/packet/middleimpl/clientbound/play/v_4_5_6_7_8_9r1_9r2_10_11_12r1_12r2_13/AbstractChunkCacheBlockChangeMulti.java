package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;

public abstract class AbstractChunkCacheBlockChangeMulti extends MiddleBlockChangeMulti {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public AbstractChunkCacheBlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void handleReadData() {
		CachedChunk cachedChunk = chunkCache.get(chunkCoord);

		if (cachedChunk == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		for (Record record : records) {
			cachedChunk.setBlock(record.y >> 4, CachedChunk.getBlockIndex(record.x, record.y & 0xF, record.z), (short) record.id);
		}
	}

}
