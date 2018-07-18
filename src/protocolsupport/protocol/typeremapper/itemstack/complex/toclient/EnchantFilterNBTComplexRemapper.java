package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NetworkItemStack;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class EnchantFilterNBTComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("ench", NBTTagType.LIST)) {
			tag.setList("ench", filterEnchantList(version, tag.getList("ench", NBTTagType.COMPOUND)));
		}
		if (tag.hasKeyOfType("stored-enchants", NBTTagType.LIST)) {
			tag.setList("stored-enchants", filterEnchantList(version, tag.getList("stored-enchants", NBTTagType.COMPOUND)));
		}
		return tag;
	}

	private NBTTagListWrapper filterEnchantList(ProtocolVersion version, NBTTagListWrapper oldList) {
		GenericSkippingTable<String> enchSkip = IdSkipper.ENCHANT.getTable(version);
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
