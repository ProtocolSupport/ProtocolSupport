package protocolsupport.protocol.typeremapper.itemstack.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.typeskipper.id.SkippingTable.IntSkippingTable;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class EnchantFilterNBTSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("ench", NBTTagCompoundWrapper.TYPE_LIST)) {
			tag.setList("ench", filterEnchantList(version, tag.getList("ench", NBTTagCompoundWrapper.TYPE_COMPOUND)));
		}
		if (tag.hasKeyOfType("stored-enchants", NBTTagCompoundWrapper.TYPE_LIST)) {
			tag.setList("stored-enchants", filterEnchantList(version, tag.getList("stored-enchants", NBTTagCompoundWrapper.TYPE_COMPOUND)));
		}
		return tag;
	}

	private NBTTagListWrapper filterEnchantList(ProtocolVersion version, NBTTagListWrapper oldList) {
		IntSkippingTable enchSkip = IdSkipper.ENCHANT.getTable(version);
		NBTTagListWrapper newList = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			if (!enchSkip.shouldSkip(enchData.getNumber("id") & 0xFFFF)) {
				newList.addCompound(enchData);
			}
		}
		return newList;
	}

}
