package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;

public abstract class AbstractChunkCacheBlockTileUpdate extends MiddleBlockTileUpdate {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	protected AbstractChunkCacheBlockTileUpdate(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		Position position = tile.getPosition();
		int x = position.getX();
		int z = position.getZ();
		LimitedHeightCachedChunk cachedChunk = chunkCache.get(ChunkCoord.fromGlobal(x, z));
		if (cachedChunk == null) {
			throw MiddlePacketCancelException.INSTANCE;
		}
		int y = position.getY();
		int sectionNumber = y >> 4;
		tile =
			tileRemapper.tileThatNeedsBlockData(tile.getType()) ?
			tileRemapper.remap(tile, cachedChunk.getBlock(sectionNumber, LimitedHeightCachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF))) :
			tileRemapper.remap(tile);
		cachedChunk.getTiles(sectionNumber).put(position, tile);
	}

}
