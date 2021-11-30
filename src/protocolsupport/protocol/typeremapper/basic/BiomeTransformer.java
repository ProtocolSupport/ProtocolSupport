package protocolsupport.protocol.typeremapper.basic;

import java.text.MessageFormat;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.GenericMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.ServerPlatform;

//TODO: control API
public class BiomeTransformer {

	private BiomeTransformer() {
	}

	public static final BiomeRemappingRegistry REGISTRY = new BiomeRemappingRegistry();
	public static final Object2IntMap<NamespacedKey> LEGACY = new Object2IntLinkedOpenHashMap<>();
	public static final int LEGACY_NONE = -1;

	public static class BiomeRemappingRegistry extends GenericMappingRegistry<NamespacedKey, GenericMappingTable<NamespacedKey>> {

		public BiomeRemappingRegistry() {
			register(Biome.BASALT_DELTAS, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.CRIMSON_FOREST, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.SOUL_SAND_VALLEY, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.WARPED_FOREST, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
		}

		protected void register(Biome from, Biome to, ProtocolVersion... versions) {
			register(from.getKey(), to.getKey(), versions);
		}

		@Override
		protected GenericMappingTable<NamespacedKey> createTable() {
			return new GenericMappingTable<>();
		}

	}

	protected static void registerLegacy(Biome biome, int id) {
		LEGACY.put(biome.getKey(), id);
	}

	static {
		LEGACY.defaultReturnValue(LEGACY_NONE);
		registerLegacy(Biome.OCEAN, 0);
		registerLegacy(Biome.PLAINS, 1);
		registerLegacy(Biome.DESERT, 2);
		registerLegacy(Biome.WINDSWEPT_HILLS, 3);
		registerLegacy(Biome.FOREST, 4);
		registerLegacy(Biome.TAIGA, 5);
		registerLegacy(Biome.SWAMP, 6);
		registerLegacy(Biome.RIVER, 7);
		registerLegacy(Biome.NETHER_WASTES, 8);
		registerLegacy(Biome.THE_END, 9);
		registerLegacy(Biome.FROZEN_OCEAN, 10);
		registerLegacy(Biome.FROZEN_RIVER, 11);
		registerLegacy(Biome.SNOWY_PLAINS, 12);
//		registerLegacy(Biome.SNOWY_MOUNTAINS, 13);
		registerLegacy(Biome.MUSHROOM_FIELDS, 14);
//		registerLegacy(Biome.MUSHROOM_FIELD_SHORE, 15);
		registerLegacy(Biome.BEACH, 16);
//		registerLegacy(Biome.DESERT_HILLS, 17);
//		registerLegacy(Biome.WOODED_HILLS, 18);
//		registerLegacy(Biome.TAIGA_HILLS, 19);
//		registerLegacy(Biome.MOUNTAIN_EDGE, 20);
		registerLegacy(Biome.JUNGLE, 21);
//		registerLegacy(Biome.JUNGLE_HILLS, 22);
		registerLegacy(Biome.SPARSE_JUNGLE, 23);
		registerLegacy(Biome.DEEP_OCEAN, 24);
		registerLegacy(Biome.STONY_SHORE, 25);
		registerLegacy(Biome.SNOWY_BEACH, 26);
		registerLegacy(Biome.BIRCH_FOREST, 27);
//		registerLegacy(Biome.BIRCH_FOREST_HILLS, 28);
		registerLegacy(Biome.DARK_FOREST, 29);
		registerLegacy(Biome.SNOWY_TAIGA, 30);
//		registerLegacy(Biome.SNOWY_TAIGA_HILLS, 31);
		registerLegacy(Biome.OLD_GROWTH_PINE_TAIGA, 32);
//		registerLegacy(Biome.GIANT_TREE_TAIGA_HILLS, 33);
		registerLegacy(Biome.WINDSWEPT_FOREST, 34);
		registerLegacy(Biome.SAVANNA, 35);
		registerLegacy(Biome.SAVANNA_PLATEAU, 36);
		registerLegacy(Biome.BADLANDS, 37);
		registerLegacy(Biome.WOODED_BADLANDS, 38);
//		registerLegacy(Biome.BADLANDS_PLATEAU, 39);
		registerLegacy(Biome.SMALL_END_ISLANDS, 40);
		registerLegacy(Biome.END_MIDLANDS, 41);
		registerLegacy(Biome.END_HIGHLANDS, 42);
		registerLegacy(Biome.END_BARRENS, 43);
		registerLegacy(Biome.WARM_OCEAN, 44);
		registerLegacy(Biome.LUKEWARM_OCEAN, 45);
		registerLegacy(Biome.COLD_OCEAN, 46);
//		registerLegacy(Biome.DEEP_WARM_OCEAN, 47);
		registerLegacy(Biome.DEEP_LUKEWARM_OCEAN, 48);
		registerLegacy(Biome.DEEP_COLD_OCEAN, 49);
		registerLegacy(Biome.DEEP_FROZEN_OCEAN, 50);
		registerLegacy(Biome.THE_VOID, 127);
		registerLegacy(Biome.SUNFLOWER_PLAINS, 129);
//		registerLegacy(Biome.DESERT_LAKES, 130);
		registerLegacy(Biome.WINDSWEPT_GRAVELLY_HILLS, 131);
		registerLegacy(Biome.FLOWER_FOREST, 132);
//		registerLegacy(Biome.TAIGA_MOUNTAINS, 133);
//		registerLegacy(Biome.SWAMP_HILLS, 134);
		registerLegacy(Biome.ICE_SPIKES, 140);
//		registerLegacy(Biome.MODIFIED_JUNGLE, 149);
//		registerLegacy(Biome.MODIFIED_JUNGLE_EDGE, 151);
		registerLegacy(Biome.OLD_GROWTH_BIRCH_FOREST, 155);
//		registerLegacy(Biome.TALL_BIRCH_HILLS, 156);
//		registerLegacy(Biome.DARK_FOREST_HILLS, 157);
//		registerLegacy(Biome.SNOWY_TAIGA_MOUNTAINS, 158);
		registerLegacy(Biome.OLD_GROWTH_SPRUCE_TAIGA, 160);
//		registerLegacy(Biome.GIANT_SPRUCE_TAIGA_HILLS, 161);
//		registerLegacy(Biome.MODIFIED_GRAVELLY_MOUNTAINS, 162);
		registerLegacy(Biome.WINDSWEPT_SAVANNA, 163);
//		registerLegacy(Biome.SHATTERED_SAVANNA_PLATEAU, 164);
		registerLegacy(Biome.ERODED_BADLANDS, 165);
//		registerLegacy(Biome.MODIFIED_WOODED_BADLANDS_PLATEAU, 166);
//		registerLegacy(Biome.MODIFIED_BADLANDS_PLATEAU, 167);
		registerLegacy(Biome.BAMBOO_JUNGLE, 168);
//		registerLegacy(Biome.BAMBOO_JUNGLE_HILLS, 169);
	}

	protected static NamespacedKey getBiomeKey(IBiomeRegistry biomeRegistry, int biomeId) {
		NamespacedKey biome = biomeRegistry.getBiomeKey(biomeId);
		if (biome == null) {
			biome = biomeRegistry.getAnyBiomeKey();
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format("Unknown biome id {0}, defaulting to {1}", biomeId, biome));
			}
		}
		return biome;
	}

	public static int mapCustomBiome(IBiomeRegistry biomeRegistry, GenericMappingTable<NamespacedKey> table, int biomeId) {
		NamespacedKey biome = getBiomeKey(biomeRegistry, biomeId);
		biomeId = biomeRegistry.getBiomeId(table.get(biome));
		if (biomeId == -1) {
			biomeId = biomeRegistry.getAnyBiomeId();
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format("No biome id mapping for biome {0}, defaulting to {1}", biome, biomeId));
			}
		}
		return biomeId;
	}

	protected static final int legacy_default = LEGACY.values().iterator().nextInt();

	public static int mapLegacyBiome(IBiomeRegistry biomeRegistry, GenericMappingTable<NamespacedKey> table, int biomeId) {
		NamespacedKey biome = table.get(getBiomeKey(biomeRegistry, biomeId));
		int legacyId = LEGACY.getInt(biome);
		if (legacyId != LEGACY_NONE) {
			return legacyId;
		} else {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format("No biome id mapping for biome {0}, defaulting to {1}", biome, legacy_default));
			}
			return legacy_default;
		}
	}

}
