package protocolsupport.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

public class ConnectionCloseEvent extends Event {

	private final Connection connection;
	public ConnectionCloseEvent(Connection connection) {
		super(true);
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
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
