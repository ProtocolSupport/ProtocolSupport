package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class AbstractBlockChangeSingle extends MiddleBlockChangeSingle {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public AbstractBlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		if (y >= 256) {
			return false;
		}
		CachedChunk cachedChunk = cache.getChunkCache().get(ChunkCoord.fromGlobal(x, z));
		if (cachedChunk != null) {
			cachedChunk.setBlock(y >> 4, CachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF), (short) id);
			return true;
		} else {
			return false;
		}
	}

}