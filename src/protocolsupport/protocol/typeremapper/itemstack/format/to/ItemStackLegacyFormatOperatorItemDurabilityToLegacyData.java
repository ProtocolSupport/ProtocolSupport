package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorItemDurabilityToLegacyData implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag != null) {
			NBTNumber damageTag = rootTag.getNumberTagOrNull(CommonNBT.ITEM_DAMAGE);
			if (damageTag != null) {
				itemstack.setLegacyData(damageTag.getAsInt());
			}
		}
		return itemstack;
	}


}
