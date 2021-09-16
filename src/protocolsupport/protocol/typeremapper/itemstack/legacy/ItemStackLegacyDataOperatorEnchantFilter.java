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
			filterEnchantList(enchSkipTable, rootTag.getCompoundListTagOrNull(CommonNBT.MODERN_ENCHANTMENTS));
			filterEnchantList(enchSkipTable, rootTag.getCompoundListTagOrNull(CommonNBT.BOOK_ENCHANTMENTS));
		}
		return itemstack;
	}

	protected static void filterEnchantList(GenericSkippingTable<String> enchSkipTable, NBTList<NBTCompound> enchList) {
		if ((enchList == null) || enchList.isEmpty()) {
			return;
		}
		for (int i = enchList.size() - 1; i >= 0; i--) {
			String enchId = enchList.getTag(i).getStringTagValueOrNull("id");
			if ((enchId == null) || enchSkipTable.isSet(enchId)) {
				enchList.removeTag(i);
			}
		}
		if (enchList.isEmpty()) {
			enchList.addTag(CommonNBT.createFakeEnchantmentTag());
		}
	}

}
