package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public abstract class ItemStackNBTSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (!tag.isNull()) {
			itemstack.setTag(remapTag(version, locale, itemstack, tag));
		}
		return itemstack;
	}

	public abstract NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag);

}
