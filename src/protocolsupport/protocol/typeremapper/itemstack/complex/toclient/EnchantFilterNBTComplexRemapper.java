package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.itemstack.complex.CommonTagNames;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class EnchantFilterNBTComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType(CommonTagNames.MODERN_ENCHANTMENTS, NBTTagType.LIST)) {
			tag.setList(CommonTagNames.MODERN_ENCHANTMENTS, filterEnchantList(version, tag.getList(CommonTagNames.MODERN_ENCHANTMENTS, NBTTagType.COMPOUND)));
		}
		if (tag.hasKeyOfType(CommonTagNames.BOOK_ENCHANTMENTS, NBTTagType.LIST)) {
			tag.setList(CommonTagNames.BOOK_ENCHANTMENTS, filterEnchantList(version, tag.getList(CommonTagNames.BOOK_ENCHANTMENTS, NBTTagType.COMPOUND)));
		}
		return tag;
	}

	protected NBTTagListWrapper filterEnchantList(ProtocolVersion version, NBTTagListWrapper oldList) {
		GenericSkippingTable<String> enchSkip = GenericIdSkipper.ENCHANT.getTable(version);
		NBTTagListWrapper newList = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			if (!enchSkip.shouldSkip(enchData.getString("id"))) {
				newList.addCompound(enchData);
			}
		}
		return newList;
	}

}
