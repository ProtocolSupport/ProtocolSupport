package protocolsupport.protocol.typeskipper.id;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeskipper.id.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeskipper.id.SkippingTable.IntSkippingTable;

public abstract class SkippingRegistry<T extends SkippingTable> {

	private final EnumMap<ProtocolVersion, T> registry = new EnumMap<>(ProtocolVersion.class);

	public SkippingRegistry() {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			registry.put(version, createTable());
		}
	}

	public T getTable(ProtocolVersion version) {
		return registry.get(version);
	}

	protected abstract T createTable();

	public static abstract class IntSkippingRegistry<T extends IntSkippingTable> extends SkippingRegistry<T> {

		public void registerSkipEntry(int id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setSkip(id);
			}
		}

	}

	public static abstract class GenericSkippingRegistry<T, R extends GenericSkippingTable<T>> extends SkippingRegistry<R> {

		public void registerSkipEntry(T id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setSkip(id);
			}
		}

	}

}
