package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class EnchantFromPEEnchantRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		if((itemstack.getTag() != null) && !itemstack.getTag().isNull()) {
			NBTTagCompoundWrapper tag = itemstack.getTag();
			if (tag.hasKeyOfType("ench", NBTTagType.LIST)) {
				tag.setList("ench", remapEnchantList(tag.getList("ench", NBTTagType.COMPOUND)));
			}
			if (tag.hasKeyOfType("stored-enchants", NBTTagType.LIST)) {
				tag.setList("stored-enchants", remapEnchantList(tag.getList("stored-enchants", NBTTagType.COMPOUND)));
			}
			itemstack.setTag(tag);
			if (itemstack.getType() == Material.ARROW) { itemstack.setType(Material.TIPPED_ARROW); }
		}
		return itemstack;
	}

	private NBTTagListWrapper remapEnchantList(NBTTagListWrapper oldList) {
		NBTTagListWrapper newList = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			enchData.setInt("id", PEDataValues.peToPcEnchant(enchData.getShortNumber("id")));
			newList.addCompound(enchData);
		}
		return newList;
	}

}