package protocolsupport.protocol.transformer.v_1_7.utils;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ChatJsonConverter {

	public static String convert(String message) {
		BaseComponent[] components = ComponentSerializer.parse(message);
		BaseComponent[] newcomponents = new BaseComponent[components.length];
		for (int i = 0; i < components.length; i++) {
			BaseComponent component = components[i];
			BaseComponent newcomponent = fixComponent(component);
			List<BaseComponent> extra = newcomponent.getExtra();
			List<BaseComponent> newextra = new ArrayList<BaseComponent>();
			if (extra != null) {
				for (BaseComponent child : extra) {
					newextra.add(fixComponent(child));
				}
				newcomponent.setExtra(newextra);
			}
			newcomponents[i] = newcomponent;
		}
		return ComponentSerializer.toString(components);
	}

	private static BaseComponent fixComponent(BaseComponent component) {
		HoverEvent event = component.getHoverEvent();
		if (event != null) {
			if (event.getAction() == HoverEvent.Action.SHOW_ITEM) {
				try {
					String itemstackjson = event.getValue()[0].toPlainText();
					NBTTagCompound compound = MojangsonParser.parse(itemstackjson);
					String itemid = compound.getString("id");
					Item item = Item.REGISTRY.get(new MinecraftKey(itemid));
					if (item != null) {
						compound.setInt("id", Item.getId(item));
					}
					component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[] { new TextComponent(compound.toString()) } ));
				} catch (Throwable t) {
					if (MinecraftServer.getServer().isDebugging()) {
						t.printStackTrace();
					}
				}
			}
		}
		return component;
	}

}
