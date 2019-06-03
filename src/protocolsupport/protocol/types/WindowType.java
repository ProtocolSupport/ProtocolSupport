package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public enum WindowType {

	GENERIC_9X1,
	GENERIC_9X2,
	GENERIC_9X3,
	GENERIC_9X4,
	GENERIC_9X5,
	GENERIC_9X6,
	GENERIC_3X3,
	ANVIL,
	BEACON,
	BLAST_FURNACE,
	BREWING_STAND,
	CRAFTING,
	ENCHANTMENT,
	FURNACE,
	GRINDSTONE,
	HOPPER,
	LECTERN,
	LOOM,
	MERCHANT,
	SHULKER_BOX,
	SMOKER,
	CARTOGRAPHY,
	STONECUTTER,
	//specials (don't actually exist at network level)
	HORSE,
	PLAYER;

	public static final EnumConstantLookup<WindowType> CONSTANT_LOOKUP = new EnumConstantLookup<>(WindowType.class);

}