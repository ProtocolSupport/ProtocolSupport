package protocolsupport.api.events;

import org.bukkit.event.Event;

import protocolsupport.api.Connection;

public abstract class ConnectionEvent extends Event {

	private final Connection connection;
	public ConnectionEvent(Connection connection) {
		super(true);
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

}
