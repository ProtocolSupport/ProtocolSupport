package protocolsupport.protocol.utils;

import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class CommonNBT {

	public static final String DISPLAY = "display";
	public static final String DISPLAY_NAME = "Name";
	public static final String DISPLAY_LORE = "Lore";

	public static NBTCompound getOrCreateDisplayTag(NBTCompound rootTag) {
		NBTCompound display = rootTag.getTagOfType(DISPLAY, NBTType.COMPOUND);
		if (display == null) {
			display = new NBTCompound();
			rootTag.setTag(DISPLAY, display);
		}
		return display;
	}

	public static final String BOOK_ENCHANTMENTS = "StoredEnchantments";

	public static final String MODERN_ENCHANTMENTS = "Enchantments";

	public static final String LEGACY_ENCHANTMENTS = "ench";

}
