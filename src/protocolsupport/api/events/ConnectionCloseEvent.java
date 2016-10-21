package protocolsupport.api.events;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

public class ConnectionCloseEvent extends ConnectionEvent {

	public ConnectionCloseEvent(Connection connection) {
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

}
