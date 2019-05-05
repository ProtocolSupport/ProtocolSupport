package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.bukkit.enchantments.Enchantment;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class EnchantFromLegacyIdComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> enchTag = tag.getTagListOfType(CommonNBT.LEGACY_ENCHANTMENTS, NBTType.COMPOUND);
		if (enchTag != null) {
			tag.removeTag(CommonNBT.LEGACY_ENCHANTMENTS);
			tag.setTag(CommonNBT.MODERN_ENCHANTMENTS, remapEnchantList(enchTag));
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
			NBTNumber enchId = enchData.getNumberTag("id");
			if (enchId != null) {
				Enchantment ench = LegacyEnchantmentId.getById(enchId.getAsInt());
				if (ench != null) {
					enchData.setTag("id", new NBTString(ench.getKey().toString()));
					newList.addTag(enchData);
				}
			}
		}
		return newList;
	}

}
