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

		if (itemAndData == null) {
			itemAndData = PEDataValues.ITEM_ID.getRemap(itemstack.getTypeId(), -1);
		}

		int itemId = itemstack.getTypeId();
		int itemData = itemstack.getData();

		if (itemAndData != null) {
			itemId = itemAndData.getI1();
			itemData = itemAndData.getI2() == -1 ? itemstack.getTypeId() : itemAndData.getI2();
		}

		itemstack.setTypeId(itemId);
		itemstack.setData(itemData);

		return itemstack;
	}

}
