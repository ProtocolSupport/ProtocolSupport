package protocolsupport.api.chat;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.utils.chat.ClickActionSerializer;
import protocolsupport.utils.chat.ComponentSerializer;
import protocolsupport.utils.chat.HoverActionSerializer;
import protocolsupport.utils.chat.ModifierSerializer;
import protocolsupport.zplatform.ServerPlatform;

public class ChatAPI {

	private static final Gson gson = new GsonBuilder()
	.registerTypeHierarchyAdapter(BaseComponent.class, new ComponentSerializer())
	.registerTypeHierarchyAdapter(Modifier.class, new ModifierSerializer())
	.registerTypeHierarchyAdapter(ClickAction.class, new ClickActionSerializer())
	.registerTypeHierarchyAdapter(HoverAction.class, new HoverActionSerializer())
	.create();

	public static BaseComponent fromJSON(String json) throws JsonParseException {
		try {
			BaseComponent result = gson.fromJson(json, BaseComponent.class);
			return result != null ? result : new TextComponent("");
		} catch (Exception e) {
			throw new JsonParseException(json, e);
		}
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
		ProtocolSupportAPI.getConnection(player).sendPacket(ServerPlatform.get().getPacketFactory().createOutboundChatPacket(messageJson, position.ordinal()));
	}

	public static class JsonParseException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public JsonParseException(String msg, Exception e) {
			super(MessageFormat.format("Unable to parse json string {0}", msg), e);
		}
	}

	public static enum MessagePosition {
		CHAT, SYSMESSAGE, HOTBAR
	}

}
