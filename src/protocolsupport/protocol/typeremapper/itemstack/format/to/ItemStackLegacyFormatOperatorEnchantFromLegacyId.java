package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorEnchantFromLegacyId extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> enchTag = tag.removeTagAndReturnIfListType(CommonNBT.LEGACY_ENCHANTMENTS, NBTCompound.class);
		if (enchTag != null) {
			tag.setTag(CommonNBT.MODERN_ENCHANTMENTS, apply(enchTag));
		}
		NBTList<NBTCompound> bookEnchTag = tag.removeTagAndReturnIfListType(CommonNBT.BOOK_ENCHANTMENTS, NBTCompound.class);
		if (bookEnchTag != null) {
			tag.setTag(CommonNBT.BOOK_ENCHANTMENTS, apply(bookEnchTag));
		}
		return tag;
	}

	protected NBTList<NBTCompound> apply(NBTList<NBTCompound> oldList) {
		NBTList<NBTCompound> newList = NBTList.createCompoundList();
		for (NBTCompound enchData : oldList.getTags()) {
			NBTNumber enchId = enchData.getNumberTagOrNull("id");
			if (enchId != null) {
				String ench = LegacyEnchantmentId.getById(enchId.getAsInt());
				if (ench != null) {
					enchData.setTag("id", new NBTString(ench));
					newList.addTag(enchData);
				}
			}
		}
		return newList;
	}

}
