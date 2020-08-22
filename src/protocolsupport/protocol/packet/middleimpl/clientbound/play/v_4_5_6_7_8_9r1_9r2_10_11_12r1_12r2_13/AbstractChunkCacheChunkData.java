package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public abstract class AbstractChunkCacheChunkData extends MiddleChunkData {

	protected final ChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public AbstractChunkCacheChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected CachedChunk cachedChunk;

	@Override
	protected void handleReadData() {
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk == null) {
			cachedChunk = chunkCache.add(coord);
		}

		boolean fullRefresh = full && cachedChunk.checkHadFull();
		if (fullRefresh) {
			full = false;
		}
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(blockMask, sectionNumber)) {
				cachedChunk.setBlocksSection(sectionNumber, new CachedChunkSectionBlockStorage(sections[sectionNumber]));
			} else if (fullRefresh) {
				cachedChunk.setBlocksSection(sectionNumber, null);
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
			cachedChunk.getTiles(sectionNumber).put(position, tile);
		}
	}

}
