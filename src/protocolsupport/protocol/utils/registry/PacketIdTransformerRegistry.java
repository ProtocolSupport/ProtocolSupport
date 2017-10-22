package protocolsupport.protocol.utils.registry;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.NoSuchElementException;

import protocolsupport.api.utils.NetworkState;

public class PacketIdTransformerRegistry {

	private final EnumMap<NetworkState, int[]> registry = new EnumMap<>(NetworkState.class);
	{
		for (NetworkState state : NetworkState.values()) {
			int[] newIds = new int[256];
			Arrays.fill(newIds, -1);
			registry.put(state, newIds);
		}
	}

	public void register(NetworkState protocol, int packetId, int newPacketId) {
		registry.get(protocol)[packetId] = newPacketId;
	}

	public int getNewPacketId(NetworkState protocol, int packetId) {
		int[] newIds = registry.get(protocol);
		if (newIds == null) {
			return -1;
		}
		int id = newIds[packetId];
		if (id == -1) {
			throw new NoSuchElementException("No packet id found for state " + protocol + " and packet id " + packetId);
		}
		return id;
	}

}
