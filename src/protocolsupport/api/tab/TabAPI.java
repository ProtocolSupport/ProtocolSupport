package protocolsupport.api.tab;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.utils.ApacheCommonsUtils;
import protocolsupport.zplatform.ServerPlatform;

public class TabAPI {

	private static int maxTabSize = Math.min(Bukkit.getMaxPlayers(), 60);

	public static int getMaxTabSize() {
		return maxTabSize;
	}

	public static void setMaxTabSize(int maxSize) {
		maxTabSize = maxSize;
	}


	private static BaseComponent currentHeader;
	private static BaseComponent currentFooter;

	public static void setDefaultHeader(BaseComponent header) {
		currentHeader = header;
	}

	public static void setDefaultFooter(BaseComponent footer) {
		currentFooter = footer;
	}

	public static BaseComponent getDefaultHeader() {
		return currentHeader;
	}

	public static BaseComponent getDefaultFooter() {
		return currentFooter;
	}

	public static void sendHeaderFooter(Player player, BaseComponent header, BaseComponent footer) {
		ApacheCommonsUtils.notNull(player, "Player can't be null");
		ProtocolSupportAPI.getConnection(player).sendPacket(ServerPlatform.get().getPacketFactory().createTabHeaderFooterPacket(header, footer));
	}

}
