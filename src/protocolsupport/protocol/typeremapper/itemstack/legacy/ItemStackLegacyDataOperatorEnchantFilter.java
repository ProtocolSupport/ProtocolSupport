package protocolsupport.protocol.typeremapper.itemstack.legacy;

import java.util.function.UnaryOperator;

import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyDataOperatorEnchantFilter implements UnaryOperator<NetworkItemStack> {

	protected final GenericSkippingTable<String> enchSkipTable;

	public ItemStackLegacyDataOperatorEnchantFilter(GenericSkippingTable<String> enchSkipTable) {
		this.enchSkipTable = enchSkipTable;
	}

	@Override
	public NetworkItemStack apply(NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag != null) {
			rootTag.setTag(CommonNBT.MODERN_ENCHANTMENTS, filterEnchantList(enchSkipTable, rootTag.getCompoundListTagOrNull(CommonNBT.MODERN_ENCHANTMENTS)));
			rootTag.setTag(CommonNBT.BOOK_ENCHANTMENTS, filterEnchantList(enchSkipTable, rootTag.getCompoundListTagOrNull(CommonNBT.BOOK_ENCHANTMENTS)));
		}
		return itemstack;
	}

	protected NBTList<NBTCompound> filterEnchantList(GenericSkippingTable<String> enchSkipTable, NBTList<NBTCompound> oldList) {
		if (oldList == null) {
			return null;
		}
		NBTList<NBTCompound> newList = NBTList.createCompoundList();
		for (NBTCompound enchData : oldList.getTags()) {
			if (!enchSkipTable.isSet(enchData.getStringTagValueOrDefault("id", ""))) {
				newList.addTag(enchData);
			}
		}
		return newList;
	}

}
