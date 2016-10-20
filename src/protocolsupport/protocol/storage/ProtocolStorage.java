package protocolsupport.protocol.storage;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import protocolsupport.protocol.ConnectionImpl;

public class ProtocolStorage {

	private static final ConcurrentHashMap<SocketAddress, ConnectionImpl> versions = new ConcurrentHashMap<>(1000);

	public static final void setConnection(SocketAddress address, ConnectionImpl connection) {
		versions.put(address, connection);
	}

	public static ConnectionImpl getConnection(SocketAddress address) {
		return versions.get(address);
	}

	public static ConnectionImpl removeConnection(SocketAddress socketAddress) {
		return versions.remove(socketAddress);
	}

	public static Collection<ConnectionImpl> getConnections() {
		return versions.values();
	}

}
