package protocolsupport.protocol.typeremapper.chunk;

import java.util.BitSet;

import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public class ChunkBlockdataLegacyWriterShort {

	private ChunkBlockdataLegacyWriterShort() {
	}

	public static byte[] serializeSections(
		IntMappingTable blockDataRemappingTable,
		LimitedHeightCachedChunk chunk, BitSet mask, boolean hasSkyLight
	) {
		int columnsCount = mask.cardinality();
		byte[] data = new byte[((hasSkyLight ? 12288 : 10240) * columnsCount)];

		int blockIdIndex = 0;
		int blockLightIndex = 8192 * columnsCount;
		int skyLightIndex = 10240 * columnsCount;

		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionIndex);

				if (section != null) {
					for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
						int blockdata = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex));
						int index = blockIdIndex + (blockIndex << 1);
						data[index] = (byte) blockdata;
						data[index + 1] = (byte) (blockdata >> 8);
					}
				}
				blockIdIndex += 8192;

				ChunkLegacyWriteUtils.copyLight(data, blockLightIndex, chunk.getBlockLight(sectionIndex));
				blockLightIndex += 2048;
				if (hasSkyLight) {
					ChunkLegacyWriteUtils.copyLight(data, skyLightIndex, chunk.getSkyLight(sectionIndex));
					skyLightIndex += 2048;
				}
			}
		}

		return data;
	}

}
