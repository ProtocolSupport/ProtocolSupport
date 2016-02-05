package protocolsupport.protocol.transformer.utils.registry;

import java.util.Arrays;
import java.util.EnumMap;

import net.minecraft.server.v1_8_R3.EnumProtocol;

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
		return newIds[packetId];
	}

}
