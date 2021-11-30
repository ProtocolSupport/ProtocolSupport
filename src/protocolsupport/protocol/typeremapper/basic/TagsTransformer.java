package protocolsupport.protocol.typeremapper.basic;

import org.bukkit.NamespacedKey;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.GenericMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TagsTransformer {

	public static final GenericMappingRegistry<String, GenericMappingTable<String>> BLOCK = new GenericMappingRegistry<>() {

		{
			register("minecraft:lava_pool_stone_cannot_replace", "minecraft:lava_pool_stone_replaceables", ProtocolVersionsHelper.DOWN_1_17_1);
		}

		@Override
		public void register(String from, String to, ProtocolVersion... versions) {
			NamespacedKey keyFrom = NamespacedKey.fromString(from);
			NamespacedKey keyTo = NamespacedKey.fromString(to);
			for (ProtocolVersion version : versions) {
				GenericMappingTable<String> table = getTable(version);
				table.set(keyFrom.toString(), keyTo.toString());
				table.set(keyFrom.getKey(), keyTo.getKey());
			}
		}

		@Override
		protected GenericMappingTable<String> createTable() {
			return new GenericMappingTable<>();
		}
	};

}
