package protocolsupport.protocol.utils.types;

import java.text.MessageFormat;
import java.util.Map;

import protocolsupport.utils.CollectionsUtils;

public enum WindowType {

	CHEST("minecraft:chest", 54, TileEntityType.CHEST),
	CRAFTING_TABLE("minecraft:crafting_table", 58),
	FURNACE("minecraft:furnace", 61, TileEntityType.FURNACE),
	DISPENSER("minecraft:dispenser", 23, TileEntityType.DISPENSER),
	ENCHANT("minecraft:enchanting_table", 116),
	BREWING("minecraft:brewing_stand", 117, TileEntityType.BREWING_STAND),
	VILLAGER("minecraft:villager"),
	BEACON("minecraft:beacon", 138, TileEntityType.BEACON),
	ANVIL("minecraft:anvil", 145),
	HOPPER("minecraft:hopper", 154, TileEntityType.HOPPER),
	DROPPER("minecraft:dropper", 158, TileEntityType.DROPPER),
	SHULKER("minecraft:shulker_box", 219, TileEntityType.SHULKER_BOX),
	HORSE("EntityHorse"),
	PLAYER("_____FAKETYPE_PLAYER");

	private final String id;
	private final int containerId;
	private final TileEntityType tileType;
	
	WindowType(String id) {
		this(id, -1);
	}
	
	WindowType(String id, int containerId) {
		this(id, containerId, TileEntityType.UNKNOWN);
	}
	
	WindowType(String id, int containerId, TileEntityType tileType) {
		this.id = id;
		this.containerId = containerId;
		this.tileType = tileType;
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
	
	public TileEntityType getTileType() {
		return tileType;
	}
	
	public int getContainerId() {
		return containerId;
	}

	public int toLegacyId() {
		return ordinal();
	}

}