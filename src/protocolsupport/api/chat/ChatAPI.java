package protocolsupport.api.chat;

import org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
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
		return json != null ? gson.fromJson(json, BaseComponent.class) : null;
	}

	public static String toJSON(BaseComponent component) {
		return component != null ? gson.toJson(component) : null;
	}

	public static void sendMessage(Player player, BaseComponent message) {
		sendMessage(player, message, MessagePosition.CHAT);
	}

	public static void sendMessage(Player player, String messageJson) {
		sendMessage(player, messageJson, MessagePosition.CHAT);
	}

	public static void sendMessage(Player player, BaseComponent message, MessagePosition position) {
		sendMessage(player, toJSON(message), position);
	}

	public static void sendMessage(Player player, String messageJson, MessagePosition position) {
		Validate.notNull(player, "Player can't be null");
		Validate.notNull(messageJson, "Message can't be null");
		Validate.notNull(position, "Message position can't be null");
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(messageJson), (byte) position.ordinal()));
	}

	public static enum MessagePosition {
		CHAT, SYSMESSAGE, HOTBAR
	}

}
