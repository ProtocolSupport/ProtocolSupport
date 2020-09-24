package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public abstract class AbstractChunkCacheChunkLight extends MiddleChunkLight {

	public AbstractChunkCacheChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ChunkCache chunkCache = cache.getChunkCache();

	protected CachedChunk cachedChunk;

	@Override
	protected void handleReadData() {
		boolean chunkLoaded = false;
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk != null) {
			chunkLoaded = true;
		} else {
			cachedChunk = chunkCache.add(coord);
		}

		for (int sectionNumber = 1; sectionNumber < (ChunkConstants.SECTION_COUNT_LIGHT - 1); sectionNumber++) {
			if (BitUtils.isIBitSet(setSkyLightMask, sectionNumber)) {
				cachedChunk.setSkyLightSection(sectionNumber - 1, skyLight[sectionNumber]);
			} else if (BitUtils.isIBitSet(emptySkyLightMask, sectionNumber)) {
				cachedChunk.setSkyLightSection(sectionNumber - 1, null);
			}

			if (BitUtils.isIBitSet(setBlockLightMask, sectionNumber)) {
				cachedChunk.setBlockLightSection(sectionNumber - 1, blockLight[sectionNumber]);
			} else if (BitUtils.isIBitSet(emptyBlockLightMask, sectionNumber)) {
				cachedChunk.setBlockLightSection(sectionNumber - 1, null);
			}
		}

		if (!chunkLoaded) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
