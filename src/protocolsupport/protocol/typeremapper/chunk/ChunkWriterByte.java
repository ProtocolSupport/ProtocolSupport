package protocolsupport.protocol.typeremapper.chunk;

import org.bukkit.block.Biome;

import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.EnumMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public class ChunkWriterByte {

	public static byte[] serializeSectionsAndBiomes(
		int mask,
		CachedChunk chunk, IdMappingTable blockDataRemappingTable, boolean hasSkyLight,
		int[] biomeData, IBiomeRegistry biomeRegistry, EnumMappingTable<Biome> biomeRemappingTable
	) {
		int columnsCount = Integer.bitCount(mask);
		byte[] data = new byte[((hasSkyLight ? 10240 : 8192) * columnsCount) + (biomeData != null ? 256 : 0)];

		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;

		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					int blockLegacyDataAcc = 0;
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
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

				ChunkWriterUtils.copyLight(data, blockLightIndex, chunk.getBlockLight(sectionNumber));
				blockLightIndex += 2048;
				if (hasSkyLight) {
					ChunkWriterUtils.copyLight(data, skyLightIndex, chunk.getSkyLight(sectionNumber));
					skyLightIndex += 2048;
				}
			}
		}

		if (biomeData != null) {
			int biomeDataOffset = data.length - 256;
			int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(biomeData);
			for (int i = 0; i < legacyBiomeData.length; i++) {
				data[biomeDataOffset + i] = (byte) BiomeRemapper.mapBiome(legacyBiomeData[i], biomeRegistry, biomeRemappingTable);
			}
		}

		return data;
	}

}
