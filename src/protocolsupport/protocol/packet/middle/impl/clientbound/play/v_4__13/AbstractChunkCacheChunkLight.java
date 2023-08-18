package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import java.util.BitSet;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractLimitedHeightChunkLight;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractChunkCacheChunkLight extends AbstractLimitedHeightChunkLight {

	protected AbstractChunkCacheChunkLight(IMiddlePacketInit init) {
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

		BitSet fullLightMask = new BitSet();
		fullLightMask.or(setSkyLightMask);
		fullLightMask.or(emptySkyLightMask);
		fullLightMask.or(setBlockLightMask);
		fullLightMask.or(emptyBlockLightMask);
		blockMask = fullLightMask.get(1, 1 + ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS);

		if (blockMask.isEmpty()) {
			throw MiddlePacketCancelException.INSTANCE;
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
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
