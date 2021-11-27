package protocolsupport.protocol.typeremapper.itemstack.format.from;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.ItemSpawnEggData;

public class ItemStackLegacyFormatOperatorSpawnEggFromIntId implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		int eggTypeId = ItemSpawnEggData.getMaterialIdBySpawnedType(LegacyEntityId.getTypeByIntId(itemstack.getLegacyData()));
		if (eggTypeId != -1) {
			itemstack.setTypeId(eggTypeId);
		}
		return itemstack;
	}

}
