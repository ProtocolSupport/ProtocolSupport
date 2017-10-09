package protocolsupport.api.tab;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.zplatform.ServerPlatform;

public class TabAPI {

	private static int maxTabSize = Math.min(Bukkit.getMaxPlayers(), 60);

	/**
	 * Returns max player list size
	 * @return max player list size
	 */
	public static int getMaxTabSize() {
		return maxTabSize;
	}

	/**
	 * Sets max player list size
	 * @param maxSize max player list size
	 */
	public static void setMaxTabSize(int maxSize) {
		maxTabSize = maxSize;
	}


	private static BaseComponent currentHeader;
	private static BaseComponent currentFooter;

	/**
	 * Sets default player list header that is sent 1 tick after player join
	 * @param header footer
	 */
	public static void setDefaultHeader(BaseComponent header) {
		currentHeader = header;
	}

	/**
	 * Sets default player list footer that is sent 1 tick after player join
	 * @param footer footer
	 */
	public static void setDefaultFooter(BaseComponent footer) {
		currentFooter = footer;
	}

	/**
	 * Returns default header <br>
	 * Returns null if header is not set
	 * @return default header
	 */
	public static BaseComponent getDefaultHeader() {
		return currentHeader;
	}

	/**
	 * Returns default footer <br>
	 * Returns null if footer is not set
	 * @return default header
	 */
	public static BaseComponent getDefaultFooter() {
		return currentFooter;
	}

	/**
	 * Sends header and footer to player
	 * @param player player
	 * @param header header
	 * @param footer footer
	 */
	public static void sendHeaderFooter(Player player, BaseComponent header, BaseComponent footer) {
		Validate.notNull(player, "Player can't be null");
		ProtocolSupportAPI.getConnection(player).sendPacket(ServerPlatform.get().getPacketFactory().createTabHeaderFooterPacket(header, footer));
	}

}
