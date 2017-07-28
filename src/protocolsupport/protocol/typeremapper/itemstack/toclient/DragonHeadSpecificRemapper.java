package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class DragonHeadSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		if (itemstack.getData() == 5) {
			itemstack.setData(3);
			NBTTagCompoundWrapper wrapper = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			wrapper.setCompound("SkullOwner", ItemStackRemapper.createDragonHeadSkullTag());
			itemstack.setTag(wrapper);
		}
		return itemstack;
	}

}
