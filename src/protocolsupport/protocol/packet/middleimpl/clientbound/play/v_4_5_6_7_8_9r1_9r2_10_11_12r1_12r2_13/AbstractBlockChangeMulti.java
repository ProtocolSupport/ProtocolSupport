package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;

public abstract class AbstractBlockChangeMulti extends MiddleBlockChangeMulti {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public AbstractBlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		CachedChunk cachedChunk = chunkCache.get(chunkCoord);
		if (cachedChunk != null) {
			for (Record record : records) {
				cachedChunk.setBlock(record.y >> 4, CachedChunk.getBlockIndex(record.x, record.y & 0xF, record.z), (short) record.id);
			}
			return true;
		} else {
			return false;
		}
	}

}
