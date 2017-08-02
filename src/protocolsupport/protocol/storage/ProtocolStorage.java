package protocolsupport.protocol.storage;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import protocolsupport.protocol.ConnectionImpl;

public class ProtocolStorage {

	private static final Map<SocketAddress, Data> primaryStorage = new ConcurrentHashMap<>(1000);
	private static final Map<SocketAddress, ConnectionImpl> secondaryStorage = new ConcurrentHashMap<>(2000);

	public static final void addConnection(SocketAddress address, ConnectionImpl connection) {
		primaryStorage.put(address, new Data(connection));
		secondaryStorage.put(address, connection);
	}

	public static final void addAddress(SocketAddress primary, SocketAddress additional) {
		Data dataentry = primaryStorage.get(primary);
		if (dataentry != null) {
			dataentry.addresses.add(additional);
			secondaryStorage.put(additional, dataentry.connection);
		}
	}

	public static ConnectionImpl getConnection(SocketAddress address) {
		return address != null ? secondaryStorage.get(address) : null;
	}

	public static ConnectionImpl removeConnection(SocketAddress address) {
		Data dataentry = primaryStorage.remove(address);
		for (SocketAddress aaddr : dataentry.addresses) {
			secondaryStorage.remove(aaddr, dataentry.connection);
		}
		secondaryStorage.remove(address, dataentry.connection);
		return dataentry.connection;
	}

	public static Collection<ConnectionImpl> getConnections() {
		return primaryStorage.values().stream().map(data -> data.connection).collect(Collectors.toList());
	}

	private static class Data {
		private final ConnectionImpl connection;
		private final Set<SocketAddress> addresses = Collections.newSetFromMap(new ConcurrentHashMap<>());
		public Data(ConnectionImpl connection) {
			this.connection = connection;
		}
	}

}
