package protocolsupport.api.events;

import org.bukkit.event.Event;

import protocolsupport.api.Connection;

public abstract class ConnectionEvent extends Event {

	private final Connection connection;

	public ConnectionEvent(Connection connection, boolean isAsync) {
		super(isAsync);
		this.connection = connection;
	}

	public ConnectionEvent(Connection connection) {
		this(connection, true);
	}

	public Connection getConnection() {
		return this.connection;
	}

}
