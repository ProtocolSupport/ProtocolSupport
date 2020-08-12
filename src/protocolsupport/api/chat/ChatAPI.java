package protocolsupport.api.chat;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ChatAPI {



	/**
	 * Converts json string to chat component<br>
	 * If json string is null, returns empty text component<br>
	 * Actually calls {@link ChatAPI#fromJSON(String, boolean)} with lenient false
	 * @param json json string
	 * @return chat component
	 * @throws JsonParseException if passed string is not in json format
	 */
	public static BaseComponent fromJSON(String json) {
		return fromJSON(json, false);
	}

	/**
	 * Converts json string to chat component<br>
	 * If json string is null, returns empty text component<br>
	 * If lenient is true and errors occurred while parsing returns text component containing input string json
	 * @param json json string
	 * @param lenient ignore errors
	 * @return chat component
	 * @throws JsonParseException if passed string is not in json format
	 */
	public static BaseComponent fromJSON(String json, boolean lenient) {
		try {
			BaseComponent result = ChatSerializer.deserialize(json);
			return result != null ? result : new TextComponent("");
		} catch (Exception e) {
			if (lenient) {
				if (ServerPlatform.get().getMiscUtils().isDebugging()) {
					ProtocolSupport.logError("Error parsing chat json " + json, e);
				}
				return new TextComponent(json);
			} else {
				throw new JsonParseException(json, e);
			}
		}
	}

	/**
	 * Converts chat component to json string
	 * @param component chatcomponent
	 * @return json string
	 */
	public static String toJSON(BaseComponent component) {
		return component != null ? ChatSerializer.serialize(ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, component) : null;
	}

	/**
	 * Sends message to player
	 * @param player player
	 * @param message chat component
	 */
	public static void sendMessage(Player player, BaseComponent message) {
		sendMessage(player, message, MessagePosition.CHAT);
	}

	/**
	 * Sends message to player
	 * @param player player
	 * @param messageJson chat json string
	 */
	public static void sendMessage(Player player, String messageJson) {
		sendMessage(player, messageJson, MessagePosition.CHAT);
	}

	/**
	 * Sends message to player<br>
	 * Allows setting position of the message
	 * @param player player
	 * @param message chat component
	 * @param position message position
	 */
	public static void sendMessage(Player player, BaseComponent message, MessagePosition position) {
		sendMessage(player, toJSON(message), position);
	}

	/**
	 * Sends message to player<br>
	 * Allows setting position of the message
	 * @param player player
	 * @param messageJson chat json string
	 * @param position message position
	 */
	public static void sendMessage(Player player, String messageJson, MessagePosition position) {
		Validate.notNull(player, "Player can't be null");
		Validate.notNull(messageJson, "Message can't be null");
		Validate.notNull(position, "Message position can't be null");
		ProtocolSupportAPI.getConnection(player).sendPacket(ServerPlatform.get().getPacketFactory().createOutboundChatPacket(messageJson, position.ordinal(), player.getUniqueId()));
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
