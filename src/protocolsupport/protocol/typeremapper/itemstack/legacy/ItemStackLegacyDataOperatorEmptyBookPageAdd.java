package protocolsupport.protocol.typeremapper.itemstack.legacy;

import java.util.function.UnaryOperator;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyDataOperatorEmptyBookPageAdd implements UnaryOperator<NetworkItemStack> {

	protected static final String emptyPageJson = ChatAPI.toJSON(new TextComponent(""));

	@Override
	public NetworkItemStack apply(NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if ((rootTag != null) && (rootTag.getStringListTagOrNull(CommonNBT.BOOK_PAGES) == null)) {
			NBTList<NBTString> pages = NBTList.createStringList();
			pages.addTag(new NBTString(emptyPageJson));
			rootTag.setTag(CommonNBT.BOOK_PAGES, pages);
		}
		return itemstack;
	}

}
