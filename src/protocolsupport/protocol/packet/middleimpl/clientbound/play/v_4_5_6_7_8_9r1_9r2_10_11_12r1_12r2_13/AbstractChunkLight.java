package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public abstract class AbstractChunkLight extends MiddleChunkLight {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public AbstractChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	protected boolean preChunk;
	protected CachedChunk cachedChunk;

	@Override
	public boolean postFromServerRead() {
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk != null) {
			preChunk = false;
		} else {
			preChunk = true;
			cachedChunk = chunkCache.add(coord);
		}

		for (int sectionNumber = 1; sectionNumber < (ChunkConstants.SECTION_COUNT_LIGHT - 1); sectionNumber++) {
			if (Utils.isBitSet(setSkyLightMask, sectionNumber)) {
				cachedChunk.setSkyLightSection(sectionNumber - 1, skyLight[sectionNumber]);
			} else if (Utils.isBitSet(emptySkyLightMask, sectionNumber)) {
				cachedChunk.setSkyLightSection(sectionNumber - 1, null);
			}

			if (Utils.isBitSet(setBlockLightMask, sectionNumber)) {
				cachedChunk.setBlockLightSection(sectionNumber - 1, blockLight[sectionNumber]);
			} else if (Utils.isBitSet(emptyBlockLightMask, sectionNumber)) {
				cachedChunk.setBlockLightSection(sectionNumber - 1, null);
			}
		}
		return true;
	}

}
