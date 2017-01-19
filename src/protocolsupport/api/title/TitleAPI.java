package protocolsupport.api.title;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.zplatform.network.PlatformPacketFactory;

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
			connection.sendPacket(PlatformPacketFactory.createTitleMainPacket(titleJson));
		}
		if (subtitleJson != null) {
			connection.sendPacket(PlatformPacketFactory.createTitleSubPacket(subtitleJson));
		}
		connection.sendPacket(PlatformPacketFactory.createTitleParamsPacket(fadeIn, stay, fadeOut));
	}

	public static void removeSimpleTitle(Player player) {
		Connection connection = ProtocolSupportAPI.getConnection(player);
		connection.sendPacket(PlatformPacketFactory.createTitleClearPacket());
		connection.sendPacket(PlatformPacketFactory.createTitleResetPacket());
	}

}
