package protocolsupport.protocol.typeremapper.itemstack.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class EmptyBookPageAdderSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (!tag.hasKeyOfType("pages", NBTTagCompoundWrapper.TYPE_LIST)) {
			NBTTagListWrapper pages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			pages.addString("");
			tag.setList("pages", pages);
		}
		return tag;
	}

}
