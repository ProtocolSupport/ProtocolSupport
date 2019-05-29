package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import org.bukkit.enchantments.Enchantment;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.NamespacedKeyUtils;

public class EnchantToLegacyIdComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> enchTag = tag.getTagListOfType(CommonNBT.MODERN_ENCHANTMENTS, NBTType.COMPOUND);
		if (enchTag != null) {
			tag.removeTag(CommonNBT.MODERN_ENCHANTMENTS);
			tag.setTag(CommonNBT.LEGACY_ENCHANTMENTS, remapEnchantList(enchTag));
		}
		NBTList<NBTCompound> bookEnch = tag.getTagListOfType(CommonNBT.BOOK_ENCHANTMENTS, NBTType.COMPOUND);
		if (bookEnch != null) {
			tag.setTag(CommonNBT.BOOK_ENCHANTMENTS, remapEnchantList(bookEnch));
		}
		return tag;
	}

	protected NBTList<NBTCompound> remapEnchantList(NBTList<NBTCompound> oldList) {
		NBTList<NBTCompound> newList = new NBTList<>(NBTType.COMPOUND);
		for (NBTCompound enchData : oldList.getTags()) {
			Enchantment ench = Enchantment.getByKey(NamespacedKeyUtils.fromString(NBTString.getValueOrNull(enchData.getTagOfType("id", NBTType.STRING))));
			if (ench != null) {
				enchData.setTag("id", new NBTShort(LegacyEnchantmentId.getId(ench)));
				newList.addTag(enchData);
			}
		}
		return newList;
	}

}
