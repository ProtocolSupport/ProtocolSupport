package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorEnchantToLegacyId extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> enchTag = tag.removeTagAndReturnIfListType(CommonNBT.MODERN_ENCHANTMENTS, NBTCompound.class);
		if (enchTag != null) {
			tag.setTag(CommonNBT.LEGACY_ENCHANTMENTS, apply(enchTag));
		}
		NBTList<NBTCompound> bookEnchTag = tag.removeTagAndReturnIfListType(CommonNBT.BOOK_ENCHANTMENTS, NBTCompound.class);
		if (bookEnchTag != null) {
			tag.setTag(CommonNBT.BOOK_ENCHANTMENTS, apply(bookEnchTag));
		}
		return tag;
	}

	protected NBTList<NBTCompound> apply(NBTList<NBTCompound> enchList) {
		for (NBTCompound enchData : enchList.getTags()) {
			enchData.setTag("id", new NBTShort(LegacyEnchantmentId.getId(enchData.getStringTagValueOrThrow("id"))));
		}
		return enchList;
	}

}
