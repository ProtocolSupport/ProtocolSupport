package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.JsonParseException;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class BookPagesToPESpecificRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
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
