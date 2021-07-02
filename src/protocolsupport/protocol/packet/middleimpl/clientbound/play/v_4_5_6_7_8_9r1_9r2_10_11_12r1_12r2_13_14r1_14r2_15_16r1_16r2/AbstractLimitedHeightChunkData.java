package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriteUtils;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightChunkData extends MiddleChunkData {

	protected AbstractLimitedHeightChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected int limitedHeightOffset;
	protected int limitedBlockMask;

	@Override
	protected void handle() {
		limitedHeightOffset = (-clientCache.getMinY()) >> 4;
		limitedBlockMask = ChunkWriteUtils.computeLimitedHeightMask(blockMask, limitedHeightOffset, ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS);
	}

}
