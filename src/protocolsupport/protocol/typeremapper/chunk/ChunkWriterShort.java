package protocolsupport.protocol.typeremapper.chunk;

import it.unimi.dsi.fastutil.ints.IntConsumer;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public class ChunkWriterShort {

	public static byte[] writeSections(
		int mask,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		int columnsCount = Integer.bitCount(mask);
		byte[] data = new byte[((hasSkyLight ? 12288 : 10240) * columnsCount) + 256];

		int blockIdIndex = 0;
		int blockLightIndex = 8192 * columnsCount;
		int skyLightIndex = 10240 * columnsCount;

		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (Utils.isBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						int blockdata = BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex));
						int index = blockIdIndex + (blockIndex << 1);
						data[index] = (byte) blockdata;
						data[index + 1] = (byte) (blockdata >> 8);
					}
				}
				blockIdIndex += 8192;

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
