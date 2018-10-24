package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class BookPagesToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTString> pages = tag.getTagListOfType("pages", NBTType.STRING);
		if (pages != null) {
			NBTList<NBTString> newPages = new NBTList<>(NBTType.STRING);
			for (NBTString page : pages.getTags()) {
				newPages.addTag(new NBTString(ChatAPI.fromJSON(page.getValue()).toLegacyText(locale)));
			}
			tag.setTag("pages", newPages);
		}
		return tag;
	}

}
