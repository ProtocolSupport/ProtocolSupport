package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import java.util.Arrays;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightChunkData extends MiddleChunkData {

	protected AbstractLimitedHeightChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handle() {
		int limitedHeightOffset = (-clientCache.getMinY()) >> 4;
		int limitedHeightOffsetEnd = limitedHeightOffset + ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS;
		mask = mask.get(limitedHeightOffset, limitedHeightOffsetEnd);
		sections = Arrays.copyOfRange(sections, limitedHeightOffset, limitedHeightOffsetEnd);
		tiles =
			Arrays.stream(tiles)
			.filter(tile -> {
				int tileY = tile.getPosition().getY();
				return tileY >= 0 && tileY < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK;
			})
			.toArray(TileEntity[]::new);
	}

}
