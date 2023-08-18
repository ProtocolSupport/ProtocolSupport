package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractLimitedHeightChunkData;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractChunkCacheChunkData extends AbstractLimitedHeightChunkData {

	protected AbstractChunkCacheChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

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

		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				cachedChunk.setBlocksSection(sectionIndex, new CachedChunkSectionBlockStorage(sections[sectionIndex].getBlockData()));
			} else {
				boolean hadBlocks = cachedChunk.resetBlocksSection(sectionIndex);
				if (hadBlocks && !full) {
					mask.set(sectionIndex);
				}
			}
		}

		for (int sectionIndex = 1; sectionIndex < (ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS - 1); sectionIndex++) {
			int offsetSectionIndex = sectionIndex - 1;
			if (setSkyLightMask.get(sectionIndex)) {
				cachedChunk.setSkyLightSection(offsetSectionIndex, skyLight[sectionIndex]);
				mask.set(offsetSectionIndex);
			} else if (emptySkyLightMask.get(sectionIndex)) {
				cachedChunk.setSkyLightSection(offsetSectionIndex, null);
				mask.set(offsetSectionIndex);
			}

			if (setBlockLightMask.get(sectionIndex)) {
				cachedChunk.setBlockLightSection(offsetSectionIndex, blockLight[sectionIndex]);
				mask.set(offsetSectionIndex);
			} else if (emptyBlockLightMask.get(sectionIndex)) {
				cachedChunk.setBlockLightSection(offsetSectionIndex, null);
				mask.set(offsetSectionIndex);
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
