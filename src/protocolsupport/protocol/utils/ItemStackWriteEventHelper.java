package protocolsupport.protocol.utils;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.types.NetworkBukkitItemStack;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;

public class ItemStackWriteEventHelper {

	private ItemStackWriteEventHelper() {
	}

	public static void callEvent(@Nonnull ProtocolVersion version, @Nonnull String locale, @Nonnull NetworkItemStack itemstack) {
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new ItemStackWriteEvent(version, locale, new NetworkBukkitItemStack(itemstack));
			Bukkit.getPluginManager().callEvent(event);
			List<String> additionalLore = event.getAdditionalLore();
			BaseComponent forcedDisplayName = event.getForcedDisplayName();
			if ((forcedDisplayName != null) || !additionalLore.isEmpty()) {
				NBTCompound rootTag = CommonNBT.getOrCreateRootTag(itemstack);
				NBTCompound displayNBT = CommonNBT.getOrCreateDisplayTag(rootTag);

				if (forcedDisplayName != null) {
					displayNBT.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatCodec.serialize(version, locale, forcedDisplayName)));
				}

				if (!additionalLore.isEmpty()) {
					NBTList<NBTString> loreNBT = displayNBT.getStringListTagOrNull(CommonNBT.DISPLAY_LORE);
					if (loreNBT == null) {
						loreNBT = NBTList.createStringList();
					}
					for (String lore : additionalLore) {
						loreNBT.addTag(new NBTString(ChatAPI.toJSON(BaseComponent.fromMessage(lore))));
					}
					displayNBT.setTag(CommonNBT.DISPLAY_LORE, loreNBT);
				}
			}
		}
	}

}
