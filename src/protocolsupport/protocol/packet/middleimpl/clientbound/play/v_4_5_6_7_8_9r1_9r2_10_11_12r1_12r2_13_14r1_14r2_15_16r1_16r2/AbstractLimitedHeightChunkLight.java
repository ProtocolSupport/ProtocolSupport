package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriteUtils;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightChunkLight extends MiddleChunkLight {

	protected AbstractLimitedHeightChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected int limitedHeightOffset;
	protected int limitedSetSkyLightMask;
	protected int limitedSetBlockLightMask;
	protected int limitedEmptySkyLightMask;
	protected int limitedEmptyBlockLightMask;

	@Override
	protected void handle() {
		limitedHeightOffset = (-clientCache.getMinY()) >> 4;

		limitedSetSkyLightMask = ChunkWriteUtils.computeLimitedHeightMask(setSkyLightMask, limitedHeightOffset, ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS);
		limitedSetBlockLightMask = ChunkWriteUtils.computeLimitedHeightMask(setBlockLightMask, limitedHeightOffset, ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS);
		limitedEmptySkyLightMask = ChunkWriteUtils.computeLimitedHeightMask(emptySkyLightMask, limitedHeightOffset, ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS);
		limitedEmptyBlockLightMask = ChunkWriteUtils.computeLimitedHeightMask(emptyBlockLightMask, limitedHeightOffset, ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS);
	}

}
