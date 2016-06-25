package protocolsupport.protocol.packet.v_1_7.utils;

import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.MinecraftKey;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.MojangsonParseException;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;

public class ChatJsonConverter {

	public static String convert(String message) {
		BaseComponent component = ChatAPI.fromJSON(message);
		walkComponent(component);
		return ChatAPI.toJSON(component);
	}

	private static void walkComponent(BaseComponent component) {
		fixComponent(component);
		for (BaseComponent sibling : component.getSiblings()) {
			walkComponent(sibling);
		}
	}

	@SuppressWarnings("deprecation")
	private static void fixComponent(BaseComponent component) {
		HoverAction hover = component.getHoverAction();
		if (hover != null && hover.getType() == HoverAction.Type.SHOW_ITEM) {
			try {
				NBTTagCompound compound = MojangsonParser.parse(hover.getValue());
				String id = compound.getString("id");
				Item item = Item.REGISTRY.get(new MinecraftKey(id));
				if (item != null) {
					compound.setInt("id", Item.getId(item));
				}
				component.setHoverAction(new HoverAction(HoverAction.Type.SHOW_ITEM, compound.toString()));
			} catch (MojangsonParseException t) {
				if (MinecraftServer.getServer().isDebugging()) {
					t.printStackTrace();
				}
			}
		}
		ClickAction click = component.getClickAction();
		if (click != null && click.getType() == ClickAction.Type.OPEN_URL) {
			String url = click.getValue();
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://"+url;
			}
			component.setClickAction(new ClickAction(ClickAction.Type.OPEN_URL, url));
		}
	}

}
