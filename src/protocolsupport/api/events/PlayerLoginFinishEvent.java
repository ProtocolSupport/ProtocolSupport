package protocolsupport.api.events;

import java.util.UUID;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.Profile;

/**
 * This event is fired when player login finishes (after online-mode processing and uuid generation, but before actual world join)
 * This event is fired only if {@link PlayerLoginStartEvent} has fired for this client
 */
public class PlayerLoginFinishEvent extends PlayerAbstractLoginEvent {

	public PlayerLoginFinishEvent(Connection connection) {
		super(connection);
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
	 * Returns true if this player logged in using online-mode checks <br>
	 * Is a shorcut to {@link Profile#isOnlineMode()}
	 * @return true if this player logged in using online-mode checks
	 */
	public boolean isOnlineMode() {
		return getConnection().getProfile().isOnlineMode();
	}

	/**
	 * Returns player uuid <br>
	 * Is a shortcut to {@link Profile#getUUID()}
	 * @return player uuid
	 */
	public UUID getUUID() {
		return getConnection().getProfile().getUUID();
	}

}
