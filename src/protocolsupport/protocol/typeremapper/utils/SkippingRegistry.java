package protocolsupport.protocol.typeremapper.utils;

import java.text.MessageFormat;
import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.IntSkippingTable;

public abstract class SkippingRegistry<T extends SkippingTable> {

	protected final EnumMap<ProtocolVersion, T> registry = new EnumMap<>(ProtocolVersion.class);

	protected SkippingRegistry() {
		clear();
	}

	public void clear() {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			registry.put(version, createTable());
		}
	}

	public T getTable(ProtocolVersion version) {
		T table = registry.get(version);
		if (table == null) {
			throw new IllegalArgumentException(MessageFormat.format("Missing skipping table for version {0}", version));
		}
		return table;
	}

	protected abstract T createTable();

	public abstract static class IntSkippingRegistry<T extends IntSkippingTable> extends SkippingRegistry<T> {

		public void register(int id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(id);
			}
		}

	}

	public abstract static class EnumSkippingRegistry<T extends Enum<T>, R extends EnumSkippingTable<T>> extends SkippingRegistry<R> {

		public void register(T id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(id);
			}
		}

	}

	public abstract static class GenericSkippingRegistry<T, R extends GenericSkippingTable<T>> extends SkippingRegistry<R> {

		public void register(T id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(id);
			}
		}

	}

}
