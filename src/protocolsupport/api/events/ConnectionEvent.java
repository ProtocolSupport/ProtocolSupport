package protocolsupport.api.events;

import java.net.InetSocketAddress;

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

	/**
	 * Returns {@link Connection} associated with this event
	 * @return connection
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Returns the client address <br>
	 * This is a shortcut to {@link Connection#getAddress()}
	 * @return client address
	 */
	public InetSocketAddress getAddress() {
		return getConnection().getAddress();
	}

}
