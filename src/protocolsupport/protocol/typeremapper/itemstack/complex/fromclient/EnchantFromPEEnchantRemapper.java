package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class EnchantFromPEEnchantRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if ((itemstack.getNBT() != null)) {
			NBTCompound tag = itemstack.getNBT();
			NBTList<NBTCompound> ench = tag.getTagListOfType("ench", NBTType.COMPOUND);
			if (ench != null) {
				tag.setTag("ench", remapEnchantList(ench));
			}
			NBTList<NBTCompound> stored_enchants = tag.getTagListOfType("stored-enchants", NBTType.COMPOUND);
			if (stored_enchants != null) {
				tag.setTag("stored-enchants", remapEnchantList(stored_enchants));
			}
			itemstack.setNBT(tag);
			//TODO WITH REMAP!
			//if (MaterialAPI.getItemByNetworkId(itemstack.getTypeId()) == Material.ARROW) { itemstack.setTypeId(MaterialAPI.getItemNetworkId(Material.TIPPED_ARROW)); }
		}
		return itemstack;
	}

	private NBTList<NBTCompound> remapEnchantList(NBTList<NBTCompound> oldList) {
		NBTList<NBTCompound> newList = new NBTList<>(NBTType.COMPOUND);
		for (int i = 0; i < oldList.size(); i++) {
			NBTCompound enchData = oldList.getTag(i);
			enchData.setTag("id", new NBTInt(PEDataValues.peToPcEnchant(enchData.getNumberTag("id").getAsInt())));
			newList.addTag(enchData);
		}
		return newList;
	}

}