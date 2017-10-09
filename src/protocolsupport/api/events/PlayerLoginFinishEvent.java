package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

/**
 * This event is fired when player login finishes (after online-mode processing and uuid generation, but before actual world join)
 * This event is fired only if {@link PlayerLoginStartEvent} has fired for this client
 */
public class PlayerLoginFinishEvent extends PlayerAbstractLoginEvent {

	private final UUID uuid;
	private final boolean onlineMode;

	public PlayerLoginFinishEvent(Connection connection, String username, UUID uuid, boolean onlineMode) {
		super(connection, username);
		this.uuid = uuid;
		this.onlineMode = onlineMode;
	}

	@Deprecated
	public PlayerLoginFinishEvent(InetSocketAddress address, String username, UUID uuid, boolean onlineMode) {
		this(ProtocolSupportAPI.getConnection(address), username, uuid, onlineMode);
	}

	/**
	 * Returns player uuid
	 * @return player uuid
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Returns true if this player logged in using online-mode checks
	 * @return true if this player logged in using online-mode checks
	 */
	public boolean isOnlineMode() {
		return onlineMode;
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
