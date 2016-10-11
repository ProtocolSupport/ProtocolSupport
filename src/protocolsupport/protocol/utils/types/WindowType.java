package protocolsupport.protocol.utils.types;

public enum WindowType {
	CHEST, CRAFTING_TABLE, FURNACE, DISPENSER, ENCHANT, BREING, VILLAGER, BEACON, ANVIL, HOPPER, DROPPER, HORSE, PLAYER;

	public static WindowType fromName(String inventoryid) {
		switch (inventoryid) {
			case "minecraft:chest":
			case "minecraft:container": {
				return CHEST;
			}
			case "minecraft:crafting_table": {
				return CRAFTING_TABLE;
			}
			case "minecraft:furnace": {
				return FURNACE;
			}
			case "minecraft:dispenser": {
				return DISPENSER;
			}
			case "minecraft:enchanting_table": {
				return ENCHANT;
			}
			case "minecraft:brewing_stand": {
				return BREING;
			}
			case "minecraft:villager": {
				return VILLAGER;
			}
			case "minecraft:beacon": {
				return BEACON;
			}
			case "minecraft:anvil": {
				return ANVIL;
			}
			case "minecraft:hopper": {
				return HOPPER;
			}
			case "minecraft:dropper": {
				return DROPPER;
			}
			case "EntityHorse": {
				return HORSE;
			}
		}
		throw new IllegalArgumentException("Don't know how to convert " + inventoryid);
	}
}