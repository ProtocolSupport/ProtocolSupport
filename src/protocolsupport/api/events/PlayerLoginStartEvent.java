package protocolsupport.api.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

/**
 * This event is fired after receiving client username (login start packet)
 */
public class PlayerLoginStartEvent extends PlayerAbstractLoginEvent {

	protected final String hostname;
	protected boolean onlinemode;

	public PlayerLoginStartEvent(Connection connection, String hostname) {
		super(connection);
		this.onlinemode = Bukkit.getOnlineMode();
		this.hostname = hostname;
	}

	/**
	 * Returns hostname which player used when connecting to server
	 * @return hostname which player used when connecting to server
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Returns true if online-mode checks will be used to auth player <br>
	 * By default returns same value as server online-mode setting
	 * @return true if online-mode checks will be used to auth player
	 */
	public boolean isOnlineMode() {
		return onlinemode;
	}

	/**
	 * Sets if online-mode checks will be used to auth player
	 * @param onlinemode if online-mode checks will be used to auth player
	 */
	public void setOnlineMode(boolean onlinemode) {
		this.onlinemode = onlinemode;
	}


	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
