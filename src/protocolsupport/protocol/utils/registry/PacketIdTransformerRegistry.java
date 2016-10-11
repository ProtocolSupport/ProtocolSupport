package protocolsupport.protocol.utils.registry;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.NoSuchElementException;

import net.minecraft.server.v1_10_R1.EnumProtocol;

public class PacketIdTransformerRegistry {

	private final EnumMap<EnumProtocol, int[]> registry = new EnumMap<>(EnumProtocol.class);
	{
		for (EnumProtocol protocol : EnumProtocol.values()) {
			int[] newIds = new int[256];
			Arrays.fill(newIds, -1);
			registry.put(protocol, newIds);
		}
	}

	public void register(EnumProtocol protocol, int packetId, int newPacketId) {
		registry.get(protocol)[packetId] = newPacketId;
	}

	public int getNewPacketId(EnumProtocol protocol, int packetId) {
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
