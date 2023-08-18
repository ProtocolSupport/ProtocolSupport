package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2;

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
		skyLight = limitLight(skyLight, limitedHeightOffset, limitedHeightOffsetEnd);
		blockLight = limitLight(blockLight, limitedHeightOffset, limitedHeightOffsetEnd);
	}

	public static byte[][] limitLight(byte[][] light, int limitedHeightOffset, int limitedHeightOffsetEnd) {
		if (light.length < limitedHeightOffset) {
			return new byte[0][];
		}
		return Arrays.copyOfRange(light, limitedHeightOffset, Math.min(light.length, limitedHeightOffsetEnd));
	}

}
