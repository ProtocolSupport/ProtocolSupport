package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import java.util.Arrays;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightChunkLight extends MiddleChunkLight {

	protected AbstractLimitedHeightChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handle() {
		int limitedHeightOffset = (-clientCache.getMinY()) >> 4;
		int limitedHeightOffsetEnd = limitedHeightOffset + ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS;

		setSkyLightMask = setSkyLightMask.get(limitedHeightOffset, limitedHeightOffsetEnd);
		setBlockLightMask = setBlockLightMask.get(limitedHeightOffset, limitedHeightOffsetEnd);
		emptySkyLightMask = emptySkyLightMask.get(limitedHeightOffset, limitedHeightOffsetEnd);
		emptyBlockLightMask = emptyBlockLightMask.get(limitedHeightOffset, limitedHeightOffsetEnd);
		skyLight = Arrays.copyOfRange(skyLight, limitedHeightOffset, limitedHeightOffsetEnd);
		blockLight = Arrays.copyOfRange(blockLight, limitedHeightOffset, limitedHeightOffsetEnd);
	}

}
