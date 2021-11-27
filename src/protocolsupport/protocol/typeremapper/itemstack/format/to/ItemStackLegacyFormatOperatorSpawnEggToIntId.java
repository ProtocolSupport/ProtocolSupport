package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;

public class ItemStackLegacyFormatOperatorSpawnEggToIntId implements ItemStackLegacyFormatOperator {

	protected final int intId;

	public ItemStackLegacyFormatOperatorSpawnEggToIntId(int intId) {
		this.intId = intId;
	}

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		itemstack.setLegacyData(intId);
		return itemstack;
	}

}
