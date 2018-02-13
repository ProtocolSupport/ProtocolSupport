package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.JsonParseException;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class BookPagesToPESpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("pages", NBTTagType.LIST)) {
			NBTTagListWrapper pages = tag.getList("pages", NBTTagType.STRING);
			NBTTagListWrapper newpages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			for (int i = 0; i < pages.size(); i++) {
				NBTTagCompoundWrapper page = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				try {
					String fromJSON = ChatAPI.fromJSON(pages.getString(i)).toLegacyText();
					page.setString("text", fromJSON);
				} catch (JsonParseException e) {
					page.setString("text", pages.getString(i));
				}
				newpages.addCompound(page);
			}
			tag.setList("pages", newpages);
		}
		return tag;
	}

}
