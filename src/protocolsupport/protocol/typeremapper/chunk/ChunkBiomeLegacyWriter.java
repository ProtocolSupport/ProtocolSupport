package protocolsupport.protocol.typeremapper.chunk;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectionData;
import protocolsupport.protocol.types.chunk.IPalettedStorage;

public class ChunkBiomeLegacyWriter {

	public static int writeBiomes(
		ByteBuf buffer,
		IBiomeRegistry biomeRegistry, GenericMappingTable<NamespacedKey> biomeLegacyDataTable,
		ChunkSectionData[] sections
	) {
		for (ChunkSectionData section : sections) {
			IPalettedStorage biomes = section.getBiomes();
			for (int biomeIndex = 0; biomeIndex < ChunkConstants.SECTION_BIOME_COUNT; biomeIndex++) {
				VarNumberCodec.writeVarInt(buffer, BiomeTransformer.mapCustomBiome(biomeRegistry, biomeLegacyDataTable, biomes.getId(biomeIndex)));
			}
		}
		return sections.length * ChunkConstants.SECTION_BIOME_COUNT;
	}

	public static void writeLegacyBiomes(
		ByteBuf buffer,
		IBiomeRegistry biomeRegistry, GenericMappingTable<NamespacedKey> biomeLegacyDataTable,
		ChunkSectionData[] sections
	) {
		for (ChunkSectionData section : sections) {
			IPalettedStorage biomes = section.getBiomes();
			for (int biomeIndex = 0; biomeIndex < ChunkConstants.SECTION_BIOME_COUNT; biomeIndex++) {
				buffer.writeInt(BiomeTransformer.mapLegacyBiome(biomeRegistry, biomeLegacyDataTable, biomes.getId(biomeIndex)));
			}
		}
	}

	public static int[] toPerBlockBiomeData(
		IBiomeRegistry biomeRegistry, GenericMappingTable<NamespacedKey> biomeLegacyDataTable,
		IPalettedStorage biomes
	) {
		int[] legacyBiomeData = new int[256];
		for (int z = 0; z < 16; z += 4) {
			for (int x = 0; x < 16; x += 4) {
				//index is ((y << 4) | ((z >> 2) << 2) | ((x >> 2)), where x,y,z are local to section, and we use y = 0
				int biomeId = BiomeTransformer.mapLegacyBiome(biomeRegistry, biomeLegacyDataTable, biomes.getId(z | (x >> 2)));
				fillLegacyBiomeData(legacyBiomeData, x, z, biomeId);
				fillLegacyBiomeData(legacyBiomeData, x, z + 1, biomeId);
				fillLegacyBiomeData(legacyBiomeData, x, z + 2, biomeId);
				fillLegacyBiomeData(legacyBiomeData, x, z + 3, biomeId);
			}
		}
		return legacyBiomeData;
	}

	protected static void fillLegacyBiomeData(@Nonnull int[] legacyBiomeData, int x, int z, int biomeId) {
		int offset = (z << 4) | x;
		Arrays.fill(legacyBiomeData, offset, offset + 4, biomeId);
	}

}
