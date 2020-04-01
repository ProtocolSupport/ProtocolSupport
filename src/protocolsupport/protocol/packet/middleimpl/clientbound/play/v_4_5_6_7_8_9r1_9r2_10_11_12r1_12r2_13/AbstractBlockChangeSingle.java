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
	public void writeToClient() {
		int y = position.getY();
		if (y >= 256) {
			return;
		}

		int x = position.getX();
		int z = position.getZ();

		CachedChunk cachedChunk = chunkCache.get(ChunkCoord.fromGlobal(x, z));
		if (cachedChunk == null) {
			return;
		}

		cachedChunk.setBlock(y >> 4, CachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF), (short) id);
		writeToClient0();
	}

	protected abstract void writeToClient0();

}