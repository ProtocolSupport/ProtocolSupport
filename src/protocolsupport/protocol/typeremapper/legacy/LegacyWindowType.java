package protocolsupport.protocol.typeremapper.legacy;

import java.util.EnumMap;
import java.util.Map;

import protocolsupport.protocol.types.WindowType;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyWindowType {

	protected static final Map<WindowType, LegacyWindowData> data = new EnumMap<>(WindowType.class);
	static {
		data.put(WindowType.GENERIC_9X1, new LegacyWindowData(0, "minecraft:container"));
		data.put(WindowType.GENERIC_9X2, new LegacyWindowData(0, "minecraft:container"));
		data.put(WindowType.GENERIC_9X3, new LegacyWindowData(0, "minecraft:container"));
		data.put(WindowType.GENERIC_9X4, new LegacyWindowData(0, "minecraft:container"));
		data.put(WindowType.GENERIC_9X5, new LegacyWindowData(0, "minecraft:container"));
		data.put(WindowType.GENERIC_9X6, new LegacyWindowData(0, "minecraft:container"));
		data.put(WindowType.CRAFTING, new LegacyWindowData(1, "minecraft:crafting_table"));
		data.put(WindowType.FURNACE, new LegacyWindowData(2, "minecraft:furnace"));
		data.put(WindowType.GENERIC_3X3, new LegacyWindowData(3, "minecraft:dispenser"));
		data.put(WindowType.ENCHANTMENT, new LegacyWindowData(4, "minecraft:enchanting_table"));
		data.put(WindowType.BREWING_STAND, new LegacyWindowData(5, "minecraft:brewing_stand"));
		data.put(WindowType.MERCHANT, new LegacyWindowData(6, "minecraft:villager"));
		data.put(WindowType.BEACON, new LegacyWindowData(7, "minecraft:beacon"));
		data.put(WindowType.ANVIL, new LegacyWindowData(8, "minecraft:anvil"));
		data.put(WindowType.HOPPER, new LegacyWindowData(9, "minecraft:hopper"));
		data.put(WindowType.SHULKER_BOX, new LegacyWindowData(13, "minecraft:shulker_box"));
	}

	public static LegacyWindowData getData(WindowType type) {
		return data.get(type);
	}

	public static class LegacyWindowData {
		protected final int intId;
		protected final String stringId;
		protected LegacyWindowData(int intId, String stringId) {
			this.intId = intId;
			this.stringId = stringId;
		}
		public int getIntId() {
			return intId;
		}
		public String getStringId() {
			return stringId;
		}
	}

}
