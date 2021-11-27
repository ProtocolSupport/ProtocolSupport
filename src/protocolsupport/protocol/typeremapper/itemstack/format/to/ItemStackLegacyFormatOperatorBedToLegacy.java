package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;

public class ItemStackLegacyFormatOperatorBedToLegacy implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		itemstack.setLegacyData(0);
		return itemstack;
	}

}
