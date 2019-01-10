package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.utils.Profile;

/**
 * This event is fired after receiving client username (login start packet)
 */
public class PlayerLoginStartEvent extends PlayerAbstractLoginEvent {

	private final String hostname;
	private boolean onlinemode;
	private boolean useonlinemodeuuid;
	private UUID uuid;

	@Deprecated
	public PlayerLoginStartEvent(Connection connection, String username, boolean onlinemode, boolean useonlinemodeuuid, String hostname) {
		super(connection);
		this.onlinemode = onlinemode;
		this.useonlinemodeuuid = useonlinemodeuuid;
		this.hostname = hostname;
	}

	@Deprecated
	public PlayerLoginStartEvent(InetSocketAddress address, String username, boolean onlinemode, boolean useonlinemodeuuid, String hostname) {
		this(ProtocolSupportAPI.getConnection(address), username, onlinemode, useonlinemodeuuid, hostname);
	}

	public PlayerLoginStartEvent(Connection connection, String hostname) {
		super(connection);
		this.onlinemode = Bukkit.getOnlineMode();
		this.useonlinemodeuuid = onlinemode;
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

	/**
	 * Returns true if online-mode uuid will be assigned to player <by>
	 * Only used if player authed using online-mode checks <br>
	 * By default returns same value as server online-mode setting
	 * @return true if online-mode uuid will be assigned to player
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@Deprecated
	public boolean useOnlineModeUUID() {
		return useonlinemodeuuid;
	}

	/**
	 * Sets if online-mode uuid will be assigned to player <br>
	 * Only used if player authed using online-mode checks
	 * @param useonlinemodeuuid if online-mode uuid will be assigned to player
	 * @deprecated Use {@link PlayerProfileCompleteEvent#setForcedUUID(UUID)} and {@link Profile#generateOfflineModeUUID(String)}
	 */
	@Deprecated
	public void setUseOnlineModeUUID(boolean useonlinemodeuuid) {
		this.useonlinemodeuuid = useonlinemodeuuid;
	}

	/**
	 * Returns true if has forced uuid
	 * @return true if has forced uuid
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@Deprecated
	public boolean hasForcedUUID() {
		return uuid != null;
	}

	/**
	 * Sets forced uuid <br>
	 * If set to null, server-selected uuid will be used <br>
	 * This option overrides any other uuid options (like {@link #useOnlineModeUUID()})
	 * @param uuid forced uuid
	 * @deprecated Use {@link PlayerProfileCompleteEvent#setForcedUUID(UUID)}
	 */
	@Deprecated
	public void setForcedUUID(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets currently set forced uuid or null if not set <br>
	 * By default returns null
	 * @return currently set forced uuid
	 * @deprecated Use {@link PlayerProfileCompleteEvent#getForcedUUID()}
	 */
	@Deprecated
	public UUID getForcedUUID() {
		return uuid;
	}

}
