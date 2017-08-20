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

		if (itemAndData != null) {
			itemstack.setTypeId(itemAndData.getI1());

			if (itemAndData.getI2() != -1) {
				itemstack.setData(itemAndData.getI2());
			}
		}

		return itemstack;
	}

}
