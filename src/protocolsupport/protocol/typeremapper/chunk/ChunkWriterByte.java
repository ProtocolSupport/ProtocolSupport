package protocolsupport.protocol.typeremapper.chunk;

import it.unimi.dsi.fastutil.ints.IntConsumer;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public class ChunkWriterByte {

	public static byte[] writeSections(
		int mask,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		int columnsCount = Integer.bitCount(mask);
		byte[] data = new byte[((hasSkyLight ? 10240 : 8192) * columnsCount) + 256];

		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;

		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (Utils.isBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					int blockLegacyDataAcc = 0;
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						int blockdata = BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex));
						data[blockIdIndex + blockIndex] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockdata);
						byte blockLegacyData = (byte) PreFlatteningBlockIdData.getDataFromCombinedId(blockdata);
						if ((blockIndex & 1) == 0) {
							blockLegacyDataAcc = blockLegacyData;
						} else {
							blockLegacyDataAcc |= (blockLegacyData << 4);
							data[(blockIndex >> 1) + blockDataIndex] = (byte) blockLegacyDataAcc;
						}
					}
				}
				blockIdIndex += 4096;
				blockDataIndex += 2048;

				ChunkUtils.copyLight(data, blockLightIndex, chunk.getBlockLight(sectionNumber));
				blockLightIndex += 2048;
				if (hasSkyLight) {
					ChunkUtils.copyLight(data, skyLightIndex, chunk.getSkyLight(sectionNumber));
					skyLightIndex += 2048;
				}

				sectionPresentConsumer.accept(sectionNumber);
			}
		}

		return data;
	}

}
