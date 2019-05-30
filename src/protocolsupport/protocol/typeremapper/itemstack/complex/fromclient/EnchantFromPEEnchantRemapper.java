package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class EnchantFromPEEnchantRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if ((itemstack.getNBT() != null)) {
			NBTCompound tag = itemstack.getNBT();
			NBTList<NBTCompound> ench = tag.getTagListOfType(CommonNBT.LEGACY_ENCHANTMENTS, NBTType.COMPOUND);
			if (ench != null) {
				tag.setTag(CommonNBT.MODERN_ENCHANTMENTS, remapEnchantList(ench));
			}
			NBTList<NBTCompound> stored_enchants = tag.getTagListOfType(CommonNBT.BOOK_ENCHANTMENTS, NBTType.COMPOUND);
			if (stored_enchants != null) {
				tag.setTag(CommonNBT.BOOK_ENCHANTMENTS, remapEnchantList(stored_enchants));
			}
			itemstack.setNBT(tag);
		}
		return itemstack;
	}

	private NBTList<NBTCompound> remapEnchantList(NBTList<NBTCompound> oldList) {
		NBTList<NBTCompound> newList = new NBTList<>(NBTType.COMPOUND);
		for (int i = 0; i < oldList.size(); i++) {
			NBTCompound enchData = oldList.getTag(i);
			enchData.setTag("id", new NBTString(PEDataValues.peToPcEnchant(enchData.getNumberTag("id").getAsInt())));
			newList.addTag(enchData);
		}
		return newList;
	}

}