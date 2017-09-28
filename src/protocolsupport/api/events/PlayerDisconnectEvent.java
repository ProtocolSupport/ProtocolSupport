package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

/**
 * This event is fired when player disconnects <br>
 * This event is fired only if {@link PlayerLoginStartEvent} has fired for this client
 */
public class PlayerDisconnectEvent extends PlayerEvent {

	public PlayerDisconnectEvent(Connection connection, String username) {
		super(connection, username);
	}

	@Deprecated
	public PlayerDisconnectEvent(InetSocketAddress address, String username) {
		super(address, username);
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
