package protocolsupport.protocol.typeskipper.id;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;

public abstract class SkippingRegistry {

	private final EnumMap<ProtocolVersion, SkippingTable> registry = new EnumMap<ProtocolVersion, SkippingTable>(ProtocolVersion.class);

	public SkippingRegistry() {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			registry.put(version, createTable());
		}
	}

	public SkippingTable getTable(ProtocolVersion version) {
		return registry.get(version);
	}

	protected abstract SkippingTable createTable();

	protected void registerSkipEntry(int id, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			registry.get(version).setSkip(id);
		}
	}


}
