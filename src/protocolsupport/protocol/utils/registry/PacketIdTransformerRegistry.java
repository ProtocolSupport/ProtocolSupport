package protocolsupport.protocol.utils.registry;

import java.util.Arrays;
import java.util.NoSuchElementException;

import protocolsupport.api.utils.NetworkState;

public class PacketIdTransformerRegistry {

	protected final int[] registry = new int[NetworkState.values().length << 8];
	{
		Arrays.fill(registry, -1);
	}

	public void register(NetworkState protocol, int packetId, int newPacketId) {
		registry[toKey(protocol, packetId)] = newPacketId;
	}

	public int getNewPacketId(NetworkState protocol, int packetId) {
		int id = registry[toKey(protocol, packetId)];
		if (id == -1) {
			throw new NoSuchElementException("No packet id found for state " + protocol + " and packet id " + packetId);
		}
		return id;
	}

	protected static int toKey(NetworkState protocol, int packetId) {
		return (protocol.ordinal() << 8) | packetId;
	}

}
