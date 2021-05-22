package protocolsupport.protocol.typeremapper.itemstack.format;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class ItemStackNBTLegacyFormatOperator implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag != null) {
			itemstack.setNBT(apply(locale, itemstack, tag));
		}
		return itemstack;
	}

	public abstract NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag);

}
