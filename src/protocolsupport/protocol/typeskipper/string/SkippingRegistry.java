package protocolsupport.protocol.typeskipper.string;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;

public abstract class SkippingRegistry {

	private final EnumMap<ProtocolVersion, SkippingTable> registry = new EnumMap<ProtocolVersion, SkippingTable>(ProtocolVersion.class);

	public SkippingRegistry() {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			registry.put(version, new SkippingTable());
		}
	}

	public SkippingTable getTable(ProtocolVersion version) {
		return registry.get(version);
	}

	protected void registerSkipEntry(String id, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			registry.get(version).setSkip(id);
		}
	}


}
