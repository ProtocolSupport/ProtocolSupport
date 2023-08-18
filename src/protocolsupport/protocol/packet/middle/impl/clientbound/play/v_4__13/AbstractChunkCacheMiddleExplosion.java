package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import org.bukkit.util.NumberConversions;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;

public abstract class AbstractChunkCacheMiddleExplosion extends MiddleExplosion {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheMiddleExplosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
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
			LimitedHeightCachedChunk chunk = chunkCache.get(new ChunkCoord(xB >> 4, zB >> 4));
			if (chunk != null) {
				chunk.setBlock(yB >> 4, LimitedHeightCachedChunk.getBlockIndex(xB & 0xF, yB & 0xF, zB & 0xF), (short) 0);
			}
		}
	}

}
