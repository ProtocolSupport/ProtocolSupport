package protocolsupport.api.chat;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;

import protocolsupport.utils.chat.ClickActionSerializer;
import protocolsupport.utils.chat.ComponentSerializer;
import protocolsupport.utils.chat.HoverActionSerializer;
import protocolsupport.utils.chat.ModifierSerializer;

public class ChatAPI {

	private static final Gson gson = new GsonBuilder()
	.registerTypeHierarchyAdapter(BaseComponent.class, new ComponentSerializer())
	.registerTypeHierarchyAdapter(Modifier.class, new ModifierSerializer())
	.registerTypeHierarchyAdapter(ClickAction.class, new ClickActionSerializer())
	.registerTypeHierarchyAdapter(HoverAction.class, new HoverActionSerializer())
	.create();

	public static BaseComponent fromJSON(String json) {
		return gson.fromJson(json, BaseComponent.class);
	}

	public static String toJSON(BaseComponent component) {
		return gson.toJson(component);
	}

	public static void sendMessage(Player player, BaseComponent message) {
		sendMessage(player, message, MessagePosition.CHAT);
	}

	public static void sendMessage(Player player, String json) {
		sendMessage(player, json, MessagePosition.CHAT);
	}

	public static void sendMessage(Player player, BaseComponent message, MessagePosition position) {
		sendMessage(player, toJSON(message), position);
	}

	public static void sendMessage(Player player, String json, MessagePosition position) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(json), (byte) position.ordinal()));
	}

	public static enum MessagePosition {
		CHAT, SYSMESSAGE, HOTBAR
	}

}
