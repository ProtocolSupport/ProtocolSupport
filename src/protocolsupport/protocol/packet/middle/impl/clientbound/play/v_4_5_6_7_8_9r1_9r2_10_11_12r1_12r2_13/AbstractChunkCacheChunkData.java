package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLimitedHeightChunkData;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractChunkCacheChunkData extends AbstractLimitedHeightChunkData {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	protected AbstractChunkCacheChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected boolean full;
	protected LimitedHeightCachedChunk cachedChunk;

	@Override
	protected void handle() {
		super.handle();

		cachedChunk = chunkCache.get(coord);
		if (cachedChunk == null) {
			cachedChunk = chunkCache.add(coord);
		}

		full = !cachedChunk.checkHadFull();

		if (!full && mask.isEmpty() && tiles.length == 0) {
			throw MiddlePacketCancelException.INSTANCE;
		}

		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				cachedChunk.setBlocksSection(sectionIndex, new CachedChunkSectionBlockStorage(sections[sectionIndex]));
			} else {
				boolean hadBlocks = cachedChunk.resetBlocksSection(sectionIndex);
				if (hadBlocks && !full) {
					mask.set(sectionIndex);
				}
			}
		}

		for (TileEntity tile : tiles) {
			Position position = tile.getPosition();
			int y = position.getY();
			int sectionIndex = y >> 4;
			if (tileRemapper.tileThatNeedsBlockData(tile.getType())) {
				tile = tileRemapper.remap(tile, cachedChunk.getBlock(sectionIndex, LimitedHeightCachedChunk.getBlockIndex(position.getX() & 0xF, y & 0xF, position.getZ() & 0xF)));
			} else {
				tile = tileRemapper.remap(tile);
			}
			cachedChunk.getTiles(sectionIndex).put(position, tile);
		}
	}

}
