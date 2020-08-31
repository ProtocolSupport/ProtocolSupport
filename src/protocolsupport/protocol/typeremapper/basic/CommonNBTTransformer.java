package protocolsupport.protocol.typeremapper.basic;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;

public class CommonNBTTransformer {

	public static NBTList<NBTString> toLegacyChatList(NBTList<NBTString> list, String locale) {
		if (list == null) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			list.setTag(i, new NBTString(ChatAPI.fromJSON(list.getTag(i).getValue(), true).toLegacyText(locale)));
		}
		return list;
	}

	public static NBTList<NBTString> fromLegacyChatList(NBTList<NBTString> list) {
		if (list == null) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			list.setTag(i, new NBTString(ChatAPI.toJSON(new TextComponent(list.getTag(i).getValue()))));
		}
		return list;
	}

}
