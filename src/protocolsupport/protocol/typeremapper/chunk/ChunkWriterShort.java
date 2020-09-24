package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public class ChunkWriterShort {

	public static byte[] serializeSections(
		int mask,
		IdMappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight
	) {
		int columnsCount = Integer.bitCount(mask);
		byte[] data = new byte[((hasSkyLight ? 12288 : 10240) * columnsCount)];

		int blockIdIndex = 0;
		int blockLightIndex = 8192 * columnsCount;
		int skyLightIndex = 10240 * columnsCount;

		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						int blockdata = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex));
						int index = blockIdIndex + (blockIndex << 1);
						data[index] = (byte) blockdata;
						data[index + 1] = (byte) (blockdata >> 8);
					}
				}
				blockIdIndex += 8192;

				ChunkWriterUtils.copyLight(data, blockLightIndex, chunk.getBlockLight(sectionNumber));
				blockLightIndex += 2048;
				if (hasSkyLight) {
					ChunkWriterUtils.copyLight(data, skyLightIndex, chunk.getSkyLight(sectionNumber));
					skyLightIndex += 2048;
				}
			}
		}

		return data;
	}

}
