package protocolsupport.protocol.typeremapper.basic;

import java.text.MessageFormat;

import org.bukkit.block.Biome;

import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.EnumMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.EnumMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.ServerPlatform;

//TODO: remap control API
public class BiomeRemapper {

	public static final BiomeRemappingRegistry REGISTRY = new BiomeRemappingRegistry();

	public static class BiomeRemappingRegistry extends EnumMappingRegistry<Biome, EnumMappingTable<Biome>> {

		public BiomeRemappingRegistry() {
			register(Biome.BASALT_DELTAS, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.CRIMSON_FOREST, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.SOUL_SAND_VALLEY, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.WARPED_FOREST, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
		}

		@Override
		protected EnumMappingTable<Biome> createTable() {
			return new EnumMappingTable<>(Biome.class);
		}

	}

	public static int mapBiome(int biomeId, IBiomeRegistry biomeRegistry, EnumMappingTable<Biome> table) {
		Biome biome = biomeRegistry.getBiome(biomeId);
		if (biome == null) {
			biome = Biome.OCEAN;
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format("Unknown biome id {0}, defaulting to ocean", biomeId));
			}
		}
		biomeId = biomeRegistry.getBiomeId(table.get(biome));
		if (biomeId == -1) {
			biomeId = 0;
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format("No biome id mapping for biome {0}, defaulting to 0", biomeId));
			}
		}
		return biomeId;
	}

}
