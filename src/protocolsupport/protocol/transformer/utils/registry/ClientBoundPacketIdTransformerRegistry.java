package protocolsupport.protocol.transformer.utils.registry;

import java.util.Arrays;
import java.util.EnumMap;

import net.minecraft.server.v1_8_R3.EnumProtocol;

public class ClientBoundPacketIdTransformerRegistry {

	private final EnumMap<EnumProtocol, int[]> registry = new EnumMap<>(EnumProtocol.class);

	public void register(EnumProtocol protocol, int packetId, int newPacketId) {
		if (!registry.containsKey(protocol)) {
			int[] newIds = new int[256];
			Arrays.fill(newIds, -1);
			registry.put(protocol, newIds);
		}
		registry.get(protocol)[packetId] = newPacketId;
	}

	public int getNewPacketId(EnumProtocol protocol, int packetId) {
		int[] newIds = registry.get(protocol);
		if (newIds == null) {
			return -1;
		}
		return newIds[packetId];
	}

}
