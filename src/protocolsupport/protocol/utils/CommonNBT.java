package protocolsupport.protocol.utils;

import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class CommonNBT {

	public static final String DISPLAY = "display";
	public static final String DISPLAY_NAME = "Name";
	public static final String DISPLAY_LORE = "Lore";

	public static NBTTagCompoundWrapper getOrCreateDisplayTag(NBTTagCompoundWrapper rootTag) {
		if (rootTag.hasKeyOfType(DISPLAY, NBTTagType.COMPOUND)) {
			return rootTag.getCompound(DISPLAY);
		} else {
			NBTTagCompoundWrapper display = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			rootTag.setCompound(DISPLAY, display);
			return display;
		}
	}

	public static final String BOOK_ENCHANTMENTS = "StoredEnchantments";

	public static final String MODERN_ENCHANTMENTS = "Enchantments";

	public static final String LEGACY_ENCHANTMENTS = "ench";

}
