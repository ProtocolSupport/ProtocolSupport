package protocolsupport.protocol.utils.types;

import java.text.MessageFormat;
import java.util.Map;

import protocolsupport.utils.CollectionsUtils;

public enum WindowType {

	CHEST("minecraft:chest"),
	CRAFTING_TABLE("minecraft:crafting_table"),
	FURNACE("minecraft:furnace"),
	DISPENSER("minecraft:dispenser"),
	ENCHANT("minecraft:enchanting_table"),
	BREWING("minecraft:brewing_stand"),
	VILLAGER("minecraft:villager"),
	BEACON("minecraft:beacon"),
	ANVIL("minecraft:anvil"),
	HOPPER("minecraft:hopper"),
	DROPPER("minecraft:dropper"),
	HORSE("EntityHorse"),
	SHULKER("minecraft:shulker_box"),
	PLAYER("_____FAKETYPE_PLAYER");

	private final String id;
	WindowType(String id) {
		this.id = id;
	}

	private static final Map<String, WindowType> byId = CollectionsUtils.makeEnumMappingMap(WindowType.class, WindowType::getId);
	static {
		byId.put("minecraft:container", WindowType.CHEST);
	}

	public static WindowType getById(String inventoryid) {
		WindowType type = byId.get(inventoryid);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown inventory type {0}", inventoryid));
		}
		return type;
	}

	public String getId() {
		return id;
	}

	public int toLegacyId() {
		return ordinal();
	}

}