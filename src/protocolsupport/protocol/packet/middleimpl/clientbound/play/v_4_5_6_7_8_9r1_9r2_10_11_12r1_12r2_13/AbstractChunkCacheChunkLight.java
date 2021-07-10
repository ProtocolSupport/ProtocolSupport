package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import java.util.BitSet;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLimitedHeightChunkLight;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractChunkCacheChunkLight extends AbstractLimitedHeightChunkLight {

	protected AbstractChunkCacheChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected LimitedHeightCachedChunk cachedChunk;
	protected BitSet blockMask;

	@Override
	protected void handle() {
		super.handle();

		boolean chunkLoaded = false;
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk != null) {
			if (cachedChunk.isFull()) {
				chunkLoaded = true;
			}
		} else {
			cachedChunk = chunkCache.add(coord);
		}

		for (int sectionIndex = 1; sectionIndex < (ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS - 1); sectionIndex++) {
			if (setSkyLightMask.get(sectionIndex)) {
				cachedChunk.setSkyLightSection(sectionIndex - 1, skyLight[sectionIndex]);
			} else if (emptySkyLightMask.get(sectionIndex)) {
				cachedChunk.setSkyLightSection(sectionIndex - 1, null);
			}

			if (setBlockLightMask.get(sectionIndex)) {
				cachedChunk.setBlockLightSection(sectionIndex - 1, blockLight[sectionIndex]);
			} else if (emptyBlockLightMask.get(sectionIndex)) {
				cachedChunk.setBlockLightSection(sectionIndex - 1, null);
			}
		}

		if (!chunkLoaded) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		BitSet fullLightMask = new BitSet();
		fullLightMask.or(setSkyLightMask);
		fullLightMask.or(emptySkyLightMask);
		fullLightMask.or(setBlockLightMask);
		fullLightMask.or(emptyBlockLightMask);
		blockMask = fullLightMask.get(1, 1 + ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS);
	}

}
