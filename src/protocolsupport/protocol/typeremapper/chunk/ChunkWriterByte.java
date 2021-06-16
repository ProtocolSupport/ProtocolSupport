package protocolsupport.protocol.typeremapper.chunk;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public class ChunkWriterByte {

	private ChunkWriterByte() {
	}

	public static byte[] serializeSectionsAndBiomes(
		GenericMappingTable<NamespacedKey> biomeRemappingTable, IntMappingTable blockDataRemappingTable,
		IBiomeRegistry biomeRegistry, int[] biomeData,
		LimitedHeightCachedChunk chunk, int mask, boolean hasSkyLight
	) {
		int columnsCount = Integer.bitCount(mask);
		byte[] data = new byte[((hasSkyLight ? 10240 : 8192) * columnsCount) + (biomeData != null ? 256 : 0)];

		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;

		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (BitUtils.isIBitSet(mask, sectionIndex)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionIndex);

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

				ChunkWriteUtils.copyLight(data, blockLightIndex, chunk.getBlockLight(sectionIndex));
				blockLightIndex += 2048;
				if (hasSkyLight) {
					ChunkWriteUtils.copyLight(data, skyLightIndex, chunk.getSkyLight(sectionIndex));
					skyLightIndex += 2048;
				}
			}
		}

		if (biomeData != null) {
			int biomeDataOffset = data.length - 256;
			int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(biomeData);
			for (int i = 0; i < legacyBiomeData.length; i++) {
				data[biomeDataOffset + i] = (byte) BiomeRemapper.mapLegacyBiome(biomeRegistry, biomeRemappingTable, legacyBiomeData[i]);
			}
		}

		return data;
	}

}
