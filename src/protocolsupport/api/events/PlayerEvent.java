package protocolsupport.api.events;

import java.net.InetSocketAddress;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

public abstract class PlayerEvent extends ConnectionEvent {

	private final String username;

	public PlayerEvent(Connection connection, String username) {
		super(connection);
		this.username = username;
	}

	@Deprecated
	public PlayerEvent(InetSocketAddress address, String username) {
		this(ProtocolSupportAPI.getConnection(address), username);
	}

	public InetSocketAddress getAddress() {
		return getConnection().getAddress();
	}

	public String getName() {
		return username;
	}

}
