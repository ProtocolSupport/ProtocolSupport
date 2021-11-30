package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import java.util.Arrays;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2_18.AbstractMaskChunkData;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightChunkData extends AbstractMaskChunkData {

	protected AbstractLimitedHeightChunkData(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();

		int limitedHeightOffset = (-clientCache.getMinY()) >> 4;

		int limitedHeightBlocksOffsetEnd = limitedHeightOffset + ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS;
		mask = mask.get(limitedHeightOffset, limitedHeightBlocksOffsetEnd);
		sections = Arrays.copyOfRange(sections, limitedHeightOffset, limitedHeightBlocksOffsetEnd);
		tiles =
			Arrays.stream(tiles)
			.filter(tile -> {
				int tileY = tile.getPosition().getY();
				return (tileY >= 0) && (tileY < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK);
			})
			.toArray(TileEntity[]::new);

		int limitedHeightLightOffsetEnd = limitedHeightOffset + ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS;
		setSkyLightMask = setSkyLightMask.get(limitedHeightOffset, limitedHeightLightOffsetEnd);
		setBlockLightMask = setBlockLightMask.get(limitedHeightOffset, limitedHeightLightOffsetEnd);
		emptySkyLightMask = emptySkyLightMask.get(limitedHeightOffset, limitedHeightLightOffsetEnd);
		emptyBlockLightMask = emptyBlockLightMask.get(limitedHeightOffset, limitedHeightLightOffsetEnd);
		skyLight = AbstractLimitedHeightChunkLight.limitLight(skyLight, limitedHeightOffset, limitedHeightLightOffsetEnd);
		blockLight = AbstractLimitedHeightChunkLight.limitLight(blockLight, limitedHeightOffset, limitedHeightLightOffsetEnd);
	}

}
