package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class ItemIdToPEIdSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		IntTuple itemAndData = PEDataValues.ITEM_ID.getRemap(itemstack.getTypeId(), itemstack.getData());

		if (itemAndData == null) { // If itemAndData is null, then we are going to get the "-1" remapper
			// When a remapper uses -1, it means it is a "remap every sub ID to this new ID"
			itemAndData = PEDataValues.ITEM_ID.getRemap(itemstack.getTypeId(), -1);
		}

		int itemId = itemstack.getTypeId();
		int itemData = itemstack.getData();

		if (itemAndData != null) { // If it isn't null, then we are going to get the new remap
			itemId = itemAndData.getI1();
			// If it is -1, then we are going to keep the original sub ID, since it shouldn't be remapped
			itemData = itemAndData.getI2() == -1 ? itemstack.getTypeId() : itemAndData.getI2();
		}

		itemstack.setTypeId(itemId);
		itemstack.setData(itemData);

		return itemstack;
	}

}
