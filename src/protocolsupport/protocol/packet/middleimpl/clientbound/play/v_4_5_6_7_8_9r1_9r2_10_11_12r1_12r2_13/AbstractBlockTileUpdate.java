package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class AbstractBlockTileUpdate extends MiddleBlockTileUpdate {

	protected final ChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public AbstractBlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		int x = tile.getPosition().getX();
		int y = tile.getPosition().getY();
		int z = tile.getPosition().getZ();
		CachedChunk cachedChunk = chunkCache.get(ChunkCoord.fromGlobal(x, z));
		if (cachedChunk != null) {
			int sectionNumber = y >> 4;
			tile =
				tileRemapper.tileThatNeedsBlockData(tile.getType()) ?
				tileRemapper.remap(tile) :
				tileRemapper.remap(tile, cachedChunk.getBlock(sectionNumber, CachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF)));
			cachedChunk.getTiles(sectionNumber).put(tile.getPosition(), tile);
			return true;
		} else {
			return false;
		}
	}

}
