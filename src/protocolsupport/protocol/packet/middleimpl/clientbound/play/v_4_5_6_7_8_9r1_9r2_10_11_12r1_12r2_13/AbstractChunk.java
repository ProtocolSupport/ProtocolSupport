package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public abstract class AbstractChunk extends MiddleChunk {

	protected final ChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public AbstractChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected CachedChunk cachedChunk;

	@Override
	public boolean postFromServerRead() {
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk == null) {
			cachedChunk = chunkCache.add(coord);
		}

		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (Utils.isBitSet(blockMask, sectionNumber)) {
				cachedChunk.setBlocksSection(sectionNumber, new CachedChunkSectionBlockStorage(sections[sectionNumber]));
//TODO: move to section writers (so we don't waste cpu getting block twice and doing additional big loop)
//				Map<Position, TileEntity> directTiles = cachedChunk.getTiles(sectionNumber);
//				for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
//					int blockdata = <section>.getBlockData(blockIndex);
//					if (tileRemapper.usedToBeTile(blockdata)) {
//						Position position = new Position(
//							(coord.getX() << 4) + (blockIndex & 0xF),
//							(sectionNumber << 4) + ((blockIndex >> 8) & 0xF),
//							(coord.getZ() << 4) + ((blockIndex >> 4) & 0xF)
//						);
//						directTiles.put(position, tileRemapper.getLegacyTileFromBlock(position, blockdata));
//					}
//				}
			}
		}

		for (TileEntity tile : tiles) {
			Position position = tile.getPosition();
			int y = position.getY();
			int sectionNumber = y >> 4;
			if (tileRemapper.tileThatNeedsBlockData(tile.getType())) {
				tile = tileRemapper.remap(tile, cachedChunk.getBlock(sectionNumber, CachedChunk.getBlockIndex(position.getX() & 0xF, y & 0xF, position.getZ() & 0xF)));
			} else {
				tile = tileRemapper.remap(tile);
			}
			cachedChunk.getTiles(sectionNumber).put(tile.getPosition(), tile);
		}

		return true;
	}

}
