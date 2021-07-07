package protocolsupport.protocol.typeremapper.window;

import java.util.EnumMap;
import java.util.Map;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class WindowTypeIdMappingRegistry extends MappingRegistry<WindowTypeIdMappingTable> {

	public static final WindowTypeIdMappingRegistry INSTANCE = new WindowTypeIdMappingRegistry();

	public WindowTypeIdMappingRegistry() {
		register(WindowType.GENERIC_9X1, 0, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GENERIC_9X2, 1, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GENERIC_9X3, 2, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GENERIC_9X4, 3, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GENERIC_9X5, 4, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GENERIC_9X6, 5, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GENERIC_3X3, 6, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.ANVIL, 7, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.BEACON, 8, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.BLAST_FURNACE, 9, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.BREWING_STAND, 10, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.CRAFTING, 11, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.ENCHANTMENT, 12, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.FURNACE, 13, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.GRINDSTONE, 14, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.HOPPER, 15, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.LECTERN, 16, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.LOOM, 17, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.MERCHANT, 18, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.SHULKER_BOX, 19, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.SMITHING, 20, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.SMOKER, 21, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.CARTOGRAPHY, 22, ProtocolVersionsHelper.UP_1_16);
		register(WindowType.STONECUTTER, 23, ProtocolVersionsHelper.UP_1_16);

		register(WindowType.GENERIC_9X1, 0, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GENERIC_9X2, 1, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GENERIC_9X3, 2, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GENERIC_9X4, 3, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GENERIC_9X5, 4, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GENERIC_9X6, 5, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GENERIC_3X3, 6, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.ANVIL, 7, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.BEACON, 8, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.BLAST_FURNACE, 9, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.BREWING_STAND, 10, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.CRAFTING, 11, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.ENCHANTMENT, 12, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.FURNACE, 13, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.GRINDSTONE, 14, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.HOPPER, 15, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.LECTERN, 16, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.LOOM, 17, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.MERCHANT, 18, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.SHULKER_BOX, 19, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.SMOKER, 20, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.CARTOGRAPHY, 21, ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		register(WindowType.STONECUTTER, 22, ProtocolVersionsHelper.RANGE__1_14__1_15_2);

		register(WindowType.GENERIC_9X1, "minecraft:container", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.GENERIC_9X2, "minecraft:container", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.GENERIC_9X3, "minecraft:container", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.GENERIC_9X4, "minecraft:container", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.GENERIC_9X5, "minecraft:container", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.GENERIC_9X6, "minecraft:container", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.CRAFTING, "minecraft:crafting_table", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.FURNACE, "minecraft:furnace", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.GENERIC_3X3, "minecraft:dispenser", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.ENCHANTMENT, "minecraft:enchanting_table", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.BREWING_STAND, "minecraft:brewing_stand", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.MERCHANT, "minecraft:villager", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.BEACON, "minecraft:beacon", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.ANVIL, "minecraft:anvil", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.HOPPER, "minecraft:hopper", ProtocolVersionsHelper.RANGE__1_8__1_13_2);
		register(WindowType.SHULKER_BOX, "minecraft:shulker_box", ProtocolVersionsHelper.RANGE__1_8__1_13_2);

		register(WindowType.GENERIC_9X1, 0, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.GENERIC_9X2, 0, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.GENERIC_9X3, 0, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.GENERIC_9X4, 0, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.GENERIC_9X5, 0, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.GENERIC_9X6, 0, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.CRAFTING, 1, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.FURNACE, 2, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.GENERIC_3X3, 3, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.ENCHANTMENT, 4, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.BREWING_STAND, 5, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.MERCHANT, 6, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.BEACON, 7, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.ANVIL, 8, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.HOPPER, 9, ProtocolVersionsHelper.DOWN_1_7_10);
		register(WindowType.SHULKER_BOX, 13, ProtocolVersionsHelper.DOWN_1_7_10);
	}

	protected void register(WindowType type, Object id, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, id);
		}
	}

	@Override
	protected WindowTypeIdMappingTable createTable() {
		return new WindowTypeIdMappingTable();
	}

	public static class WindowTypeIdMappingTable extends MappingTable {

		protected final Map<WindowType, Object> windowId = new EnumMap<>(WindowType.class);

		public void set(WindowType type, Object id) {
			windowId.put(type, id);
		}

		public Object get(WindowType type) {
			return windowId.get(type);
		}

	}

}
