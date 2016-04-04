package protocolsupport.protocol.typeremapper.id;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;

public abstract class RemappingRegistry {

	private final EnumMap<ProtocolVersion, RemappingTable> remappings = new EnumMap<>(ProtocolVersion.class);

	public RemappingRegistry() {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			if (version.isSupported()) {
				remappings.put(version, createTable());
			}
		}
	}

	public RemappingTable getTable(ProtocolVersion version) {
		return remappings.get(version);
	}

	protected abstract RemappingTable createTable();

	protected void registerRemapEntry(int from, int to, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			remappings.get(version).setRemap(from, to);
		}
	}

}
