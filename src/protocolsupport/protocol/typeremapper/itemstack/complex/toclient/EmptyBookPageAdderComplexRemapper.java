package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class EmptyBookPageAdderComplexRemapper extends ItemStackNBTComplexRemapper {

	protected static final String emptyPageJson = ChatAPI.toJSON(new TextComponent(""));

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		if (tag.getStringListTagOrNull(CommonNBT.BOOK_PAGES) == null) {
			NBTList<NBTString> pages = NBTList.createStringList();
			pages.addTag(new NBTString(emptyPageJson));
			tag.setTag(CommonNBT.BOOK_PAGES, pages);
		}
		return tag;
	}

}
