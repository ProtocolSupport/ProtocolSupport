package protocolsupport.protocol.typeremapper.chunk;

import java.util.BitSet;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.IPalettedStorage;

public class ChunkBlockdataLegacyWriterByte {

	private ChunkBlockdataLegacyWriterByte() {
	}

	public static byte[] serializeSectionsAndBiomes(
		GenericMappingTable<NamespacedKey> biomeLegacyDataTable, IntMappingTable blockDataRemappingTable,
		IBiomeRegistry biomeRegistry, IPalettedStorage biomes,
		LimitedHeightCachedChunk chunk, BitSet mask, boolean hasSkyLight
	) {
		int columnsCount = mask.cardinality();
		byte[] data = new byte[((hasSkyLight ? 10240 : 8192) * columnsCount) + (biomes != null ? 256 : 0)];

		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;

		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionIndex);

				if (section != null) {
					int blockLegacyDataAcc = 0;
					for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
						int blockdata = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex));
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

				ChunkLegacyWriteUtils.copyLight(data, blockLightIndex, chunk.getBlockLight(sectionIndex));
				blockLightIndex += 2048;
				if (hasSkyLight) {
					ChunkLegacyWriteUtils.copyLight(data, skyLightIndex, chunk.getSkyLight(sectionIndex));
					skyLightIndex += 2048;
				}
			}
		}

		if (biomes != null) {
			int biomeDataOffset = data.length - 256;
			int[] legacyBiomeData = ChunkBiomeLegacyWriter.toPerBlockBiomeData(biomeRegistry, biomeLegacyDataTable, biomes);
			for (int i = 0; i < legacyBiomeData.length; i++) {
				data[biomeDataOffset + i] = (byte) legacyBiomeData[i];
			}
		}

		return data;
	}

}
