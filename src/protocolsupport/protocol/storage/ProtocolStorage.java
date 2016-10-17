package protocolsupport.protocol.storage;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import protocolsupport.api.Connection;

public class ProtocolStorage {

	private static final ConcurrentHashMap<SocketAddress, Connection> versions = new ConcurrentHashMap<>(1000);

	public static final void setConnection(SocketAddress address, Connection connection) {
		versions.put(address, connection);
	}

	public static Connection getConnection(SocketAddress address) {
		return versions.get(address);
	}

	public static Connection removeConnection(SocketAddress socketAddress) {
		return versions.remove(socketAddress);
	}

	public static Collection<Connection> getConnections() {
		return versions.values();
	}

}
