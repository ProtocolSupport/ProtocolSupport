package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class BookPagesFromPERemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("pages", NBTTagType.LIST)) {
			NBTTagListWrapper pages = tag.getList("pages", NBTTagType.COMPOUND);
			NBTTagListWrapper newpages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			for (int i = 0; i < pages.size(); i++) {
				newpages.addString(pages.getCompound(i).getString("text"));
			}
			tag.setList("pages", newpages);
		}
		return tag;
	}

}
