package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.IntSkippingTable;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class EnchantFilterNBTSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("ench", NBTTagType.LIST)) {
			tag.setList("ench", filterEnchantList(version, tag.getList("ench", NBTTagType.COMPOUND)));
		}
		if (tag.hasKeyOfType("stored-enchants", NBTTagType.LIST)) {
			tag.setList("stored-enchants", filterEnchantList(version, tag.getList("stored-enchants", NBTTagType.COMPOUND)));
		}
		return tag;
	}

	private NBTTagListWrapper filterEnchantList(ProtocolVersion version, NBTTagListWrapper oldList) {
		IntSkippingTable enchSkip = IdSkipper.ENCHANT.getTable(version);
		NBTTagListWrapper newList = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			if (!enchSkip.shouldSkip(enchData.getIntNumber("id") & 0xFFFF)) {
				newList.addCompound(enchData);
			}
		}
		return newList;
	}

}
