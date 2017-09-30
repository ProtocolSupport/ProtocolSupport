package protocolsupport.api.title;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.zplatform.ServerPlatform;

public class TitleAPI {

	/**
	 * Sends title, subtitle, and it's params <br>
	 * Title and subtitle can't be both null
	 * @param player Player to which title is sent
	 * @param title title chat component or null
	 * @param subtitle subtitle chat component or null
	 * @param fadeIn ticks to spend fading in
	 * @param stay ticks to display
	 * @param fadeOut ticks to spend fading out
	 */
	public static void sendSimpleTitle(Player player, BaseComponent title, BaseComponent subtitle, int fadeIn, int stay, int fadeOut) {
		sendSimpleTitle(player, ChatAPI.toJSON(title), ChatAPI.toJSON(subtitle), fadeIn, stay, fadeOut);
	}

	/**
	 * Sends title, subtitle, and it's params <br>
	 * Title and subtitle can't be both null
	 * @param player Player to which title is sent
	 * @param titleJson title chat json or null
	 * @param subtitleJson subtitle chat json or null
	 * @param fadeIn ticks to spend fading in
	 * @param stay ticks to display
	 * @param fadeOut ticks to spend fading out
	 */
	public static void sendSimpleTitle(Player player, String titleJson, String subtitleJson, int fadeIn, int stay, int fadeOut) {
		Validate.notNull(player, "Player can't be null");
		if ((titleJson == null) && (subtitleJson == null)) {
			throw new IllegalArgumentException("Title and subtitle can't be both null");
		}
		Connection connection = ProtocolSupportAPI.getConnection(player);
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleParamsPacket(fadeIn, stay, fadeOut));
		if (subtitleJson != null) {
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleSubPacket(subtitleJson));
		}
		if (titleJson == null) {
			titleJson = "";
		}
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleMainPacket(titleJson));
	}

	/**
	 * Removes and resets title, subtitle and it's params
	 * @param player Player to which reset should be sent
	 */
	public static void removeSimpleTitle(Player player) {
		Connection connection = ProtocolSupportAPI.getConnection(player);
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleClearPacket());
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleResetPacket());
	}

}
