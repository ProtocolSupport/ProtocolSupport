package protocolsupport.protocol.typeremapper.itemstack.format.from;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorMapFromLegacyId implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		CommonNBT.getOrCreateRootTag(itemstack).setTag(CommonNBT.MAP_ID, new NBTInt(itemstack.getLegacyData()));
		return itemstack;
	}

}