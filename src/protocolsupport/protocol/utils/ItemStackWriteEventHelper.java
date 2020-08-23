package protocolsupport.protocol.utils;

import java.util.List;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.types.NetworkBukkitItemStack;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class ItemStackWriteEventHelper {

	public static void callEvent(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new ItemStackWriteEvent(version, locale, new NetworkBukkitItemStack(itemstack));
			Bukkit.getPluginManager().callEvent(event);
			List<String> additionalLore = event.getAdditionalLore();
			BaseComponent forcedDisplayName = event.getForcedDisplayName();
			if ((forcedDisplayName != null) || !additionalLore.isEmpty()) {
				NBTCompound rootTag = CommonNBT.getOrCreateRootTag(itemstack);
				NBTCompound displayNBT = CommonNBT.getOrCreateDisplayTag(rootTag);

				if (forcedDisplayName != null) {
					displayNBT.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatSerializer.serialize(version, locale, forcedDisplayName)));
				}

				if (!additionalLore.isEmpty()) {
					NBTList<NBTString> loreNBT = displayNBT.getTagListOfTypeOrNull(CommonNBT.DISPLAY_LORE, NBTType.STRING);
					if (loreNBT == null) {
						loreNBT = new NBTList<>(NBTType.STRING);
					}
					for (String lore : additionalLore) {
						loreNBT.addTag(new NBTString(ChatAPI.toJSON(new TextComponent(lore))));
					}
					displayNBT.setTag(CommonNBT.DISPLAY_LORE, loreNBT);
				}
			}
		}
	}

}
