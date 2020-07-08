package protocolsupport.protocol.typeremapper.basic;

import org.bukkit.block.Biome;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

//TODO: remap control API
public class BiomeRemapper {

	public static final BiomeRemappingRegistry REGISTRY = new BiomeRemappingRegistry();

	public static class BiomeRemappingRegistry extends IntMappingRegistry<IdMappingTable> {

		{
			register(Biome.BASALT_DELTAS, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.CRIMSON_FOREST, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.SOUL_SAND_VALLEY, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
			register(Biome.WARPED_FOREST, Biome.NETHER_WASTES, ProtocolVersionsHelper.DOWN_1_15_2);
		}

		protected void register(Biome from, Biome to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(MaterialAPI.getBiomeNetworkId(from), MaterialAPI.getBiomeNetworkId(to));
			}
		}

		@Override
		protected IdMappingTable createTable() {
			return new ArrayBasedIntMappingTable(256);
		}
	}

}
