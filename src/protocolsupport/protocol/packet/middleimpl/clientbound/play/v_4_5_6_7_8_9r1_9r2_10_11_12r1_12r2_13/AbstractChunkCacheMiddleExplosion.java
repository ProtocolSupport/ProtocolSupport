package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import org.bukkit.util.NumberConversions;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;

public abstract class AbstractChunkCacheMiddleExplosion extends MiddleExplosion {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public AbstractChunkCacheMiddleExplosion(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void handleReadData() {
		int xOrigin = NumberConversions.floor(x);
		x = xOrigin;
		int yOrigin = NumberConversions.floor(y);
		y = yOrigin;
		int zOrigin = NumberConversions.floor(z);
		z = zOrigin;
		for (Position block : blocks) {
			int xB = xOrigin + block.getX();
			int yB = yOrigin + block.getY();
			int zB = zOrigin + block.getZ();
			CachedChunk chunk = chunkCache.get(new ChunkCoord(xB >> 4, zB >> 4));
			if (chunk != null) {
				chunk.setBlock(yB >> 4, CachedChunk.getBlockIndex(xB & 0xF, yB & 0xF, zB & 0xF), (short) 0);
			}
		}
	}

}
