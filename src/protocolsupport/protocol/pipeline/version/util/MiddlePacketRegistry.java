package protocolsupport.protocol.pipeline.version.util;

import java.util.NoSuchElementException;
import java.util.function.Function;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.MiddlePacket;
import protocolsupport.protocol.packet.middle.MiddlePacket.MiddlePacketInit;

@SuppressWarnings("unchecked")
public class MiddlePacketRegistry<T extends MiddlePacket> {

	protected final MiddlePacketInit connection;

	public MiddlePacketRegistry(MiddlePacketInit connection) {
		this.connection = connection;
	}

	protected final Lazy<T>[] registry = new Lazy[NetworkState.values().length << 8];

	public void register(NetworkState state, PacketType packetType, Function<MiddlePacketInit, T> middlepacket) {
		register(state, packetType.getId(), middlepacket);
	}

	public void register(NetworkState state, int packetId, Function<MiddlePacketInit, T> middlepacket) {
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

		protected final MiddlePacketInit init;
		protected final Function<MiddlePacketInit, T> func;

		public Lazy(MiddlePacketInit init, Function<MiddlePacketInit, T> middlepacket) {
			this.init = init;
			this.func = middlepacket;
		}

		protected T instance;

		public T getInstance() {
			if (instance == null) {
				instance = func.apply(init);
			}
			return instance;
		}

	}

	protected static int toKey(NetworkState protocol, int packetId) {
		return (protocol.ordinal() << 8) | packetId;
	}

}
