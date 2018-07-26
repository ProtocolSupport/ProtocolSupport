package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.bukkit.enchantments.Enchantment;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.CommonTagNames;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class EnchantFromLegacyIdComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType(CommonTagNames.LEGACY_ENCHANTMENTS, NBTTagType.LIST)) {
			tag.setList(CommonTagNames.MODERN_ENCHANTMENTS, remapEnchantList(tag.getList(CommonTagNames.LEGACY_ENCHANTMENTS, NBTTagType.COMPOUND)));
			tag.remove(CommonTagNames.LEGACY_ENCHANTMENTS);
		}
		if (tag.hasKeyOfType(CommonTagNames.BOOK_ENCHANTMENTS, NBTTagType.LIST)) {
			tag.setList(CommonTagNames.BOOK_ENCHANTMENTS, remapEnchantList(tag.getList(CommonTagNames.BOOK_ENCHANTMENTS, NBTTagType.COMPOUND)));
		}
		return tag;
	}

	protected NBTTagListWrapper remapEnchantList(NBTTagListWrapper oldList) {
		NBTTagListWrapper newList = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			Enchantment ench = LegacyEnchantmentId.getById(enchData.getIntNumber("id") & 0xFFFF);
			if (ench != null) {
				enchData.setString("id", ench.getKey().toString());
				newList.addCompound(enchData);
			}
		}
		return newList;
	}

}
