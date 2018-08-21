package protocolsupport.protocol.utils.registry;

import java.util.NoSuchElementException;
import java.util.function.Function;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.MiddlePacket;

@SuppressWarnings("unchecked")
public class MiddlePacketRegistry<T extends MiddlePacket> {

	protected final ConnectionImpl connection;
	public MiddlePacketRegistry(ConnectionImpl connection) {
		this.connection = connection;
	}

	protected final Lazy<T>[] registry = new Lazy[NetworkState.values().length << 8];

	public void register(NetworkState state, int packetId, Function<ConnectionImpl, T> middlepacket) {
		registry[toKey(state, packetId)] = new Lazy<>(connection, middlepacket);
	}

	public T getTransformer(NetworkState state, int packetId) {
		Lazy<T> transformer = registry[toKey(state, packetId)];
		if (transformer == null) {
			throw new NoSuchElementException("No transformer found for state " + state + " and packet id " + packetId);
		}
		return transformer.getInstance();
	}

	protected static class Lazy<T> {
		protected final ConnectionImpl connection;
		protected final Function<ConnectionImpl, T> func;
		public Lazy(ConnectionImpl connection, Function<ConnectionImpl, T> middlepacket) {
			this.connection = connection;
			this.func = middlepacket;
		}

		protected T instance;
		public T getInstance() {
			if (instance == null) {
				instance = func.apply(connection);
			}
			return instance;
		}
	}

	protected static int toKey(NetworkState protocol, int packetId) {
		return (protocol.ordinal() << 8) | packetId;
	}

}
