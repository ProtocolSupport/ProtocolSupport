package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.JsonParseException;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class BookPagesToPESpecificRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTString> pages = tag.getTagListOfType("pages", NBTType.STRING);
		if (pages != null) {
			NBTList<NBTCompound> newpages = new NBTList<>(NBTType.COMPOUND);
			for (int i = 0; i < pages.size(); i++) {
				NBTCompound page = new NBTCompound();
				try {
					String fromJSON = ChatAPI.fromJSON(pages.getTag(i).getValue()).toLegacyText();
					page.setTag("text", new NBTString(fromJSON));
				} catch (JsonParseException e) {
					page.setTag("text", new NBTString(pages.getTag(i).getValue()));
				}
				newpages.addTag(page);
			}
			tag.setTag("pages", newpages);
		}
		return tag;
	}

}
