package protocolsupport.protocol.typeremapper.itemstack.serverbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class BookPagesUnescapeSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("pages", NBTTagCompoundWrapper.TYPE_LIST)) {
			NBTTagListWrapper pages = tag.getList("pages", NBTTagCompoundWrapper.TYPE_STRING);
			NBTTagListWrapper newPages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			for (int i = 0; i < pages.size(); i++) {
				newPages.addString(ChatAPI.fromJSON(pages.getString(i)).getValue());
			}
			tag.setList("pages", newPages);
		}
		return tag;
	}

}
