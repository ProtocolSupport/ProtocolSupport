package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class EnchantFromPEEnchantRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if((itemstack.getNBT() != null) && !itemstack.getNBT().isNull()) {
			NBTTagCompoundWrapper tag = itemstack.getNBT();
			if (tag.hasKeyOfType("ench", NBTTagType.LIST)) {
				tag.setList("ench", remapEnchantList(tag.getList("ench", NBTTagType.COMPOUND)));
			}
			if (tag.hasKeyOfType("stored-enchants", NBTTagType.LIST)) {
				tag.setList("stored-enchants", remapEnchantList(tag.getList("stored-enchants", NBTTagType.COMPOUND)));
			}
			itemstack.setNBT(tag);
			//TODO WITH REMAP!
			//if (MaterialAPI.getItemByNetworkId(itemstack.getTypeId()) == Material.ARROW) { itemstack.setTypeId(MaterialAPI.getItemNetworkId(Material.TIPPED_ARROW)); }
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