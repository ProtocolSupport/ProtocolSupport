package protocolsupport.api.title;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.zplatform.ServerPlatform;

public class TitleAPI {

	public static void sendSimpleTitle(Player player, BaseComponent title, BaseComponent subtitle, int fadeIn, int stay, int fadeOut) {
		sendSimpleTitle(player, ChatAPI.toJSON(title), ChatAPI.toJSON(subtitle), fadeIn, stay, fadeOut);
	}

	public static void sendSimpleTitle(Player player, String titleJson, String subtitleJson, int fadeIn, int stay, int fadeOut) {
		Validate.notNull(player, "Player can't be null");
		if ((titleJson == null) && (subtitleJson == null)) {
			throw new IllegalArgumentException("Title and subtitle can't be both null");
		}
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (titleJson != null) {
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleMainPacket(titleJson));
		}
		if (subtitleJson != null) {
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleSubPacket(subtitleJson));
		}
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleParamsPacket(fadeIn, stay, fadeOut));
	}

	public static void removeSimpleTitle(Player player) {
		Connection connection = ProtocolSupportAPI.getConnection(player);
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleClearPacket());
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createTitleResetPacket());
	}

}
