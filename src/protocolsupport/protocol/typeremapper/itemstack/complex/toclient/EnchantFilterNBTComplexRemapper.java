package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class EnchantFilterNBTComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		tag.setTag(CommonNBT.MODERN_ENCHANTMENTS, filterEnchantList(version, tag.getTagListOfTypeOrNull(CommonNBT.MODERN_ENCHANTMENTS, NBTType.COMPOUND)));
		tag.setTag(CommonNBT.BOOK_ENCHANTMENTS, filterEnchantList(version, tag.getTagListOfTypeOrNull(CommonNBT.BOOK_ENCHANTMENTS, NBTType.COMPOUND)));
		return tag;
	}

	protected NBTList<NBTCompound> filterEnchantList(ProtocolVersion version, NBTList<NBTCompound> oldList) {
		if (oldList == null) {
			return null;
		}
		GenericSkippingTable<String> enchSkip = GenericIdSkipper.ENCHANT.getTable(version);
		NBTList<NBTCompound> newList = new NBTList<>(NBTType.COMPOUND);
		for (NBTCompound enchData : oldList.getTags()) {
			if (!enchSkip.shouldSkip(enchData.getStringTagValueOrDefault("id", ""))) {
				newList.addTag(enchData);
			}
		}
		return newList;
	}

}
