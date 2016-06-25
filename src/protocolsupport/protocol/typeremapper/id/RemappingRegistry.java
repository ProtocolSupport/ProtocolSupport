package protocolsupport.protocol.typeremapper.id;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingTable.IdRemappingTable;

public abstract class RemappingRegistry<T extends RemappingTable> {

	private final EnumMap<ProtocolVersion, T> remappings = new EnumMap<>(ProtocolVersion.class);

	public RemappingRegistry() {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			if (version.isSupported()) {
				remappings.put(version, createTable());
			}
		}
	}

	public T getTable(ProtocolVersion version) {
		return remappings.get(version);
	}

	protected abstract T createTable();

	public static abstract class IdRemappingRegistry<T extends IdRemappingTable> extends RemappingRegistry<T> {

		public void registerRemapEntry(int from, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

}
