package protocolsupport.protocol.typeremapper.utils;

import java.text.MessageFormat;
import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingTable.EnumMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;

public abstract class MappingRegistry<T extends MappingTable> {

	protected final EnumMap<ProtocolVersion, T> registry = new EnumMap<>(ProtocolVersion.class);

	public MappingRegistry() {
		clear();
	}

	public void clear() {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			registry.put(version, createTable());
		}
	}

	public T getTable(ProtocolVersion version) {
		return registry.computeIfAbsent(version, k -> {
			throw new IllegalArgumentException(MessageFormat.format("Missing mapping table for version {0}", k));
		});
	}

	protected abstract T createTable();

	public abstract static class IntMappingRegistry<T extends IdMappingTable> extends MappingRegistry<T> {

		public void register(int from, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(from, to);
			}
		}

	}

	public abstract static class GenericMappingRegistry<T, R extends GenericMappingTable<T>> extends MappingRegistry<R> {

		public void register(T from, T to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(from, to);
			}
		}

	}

	public abstract static class EnumMappingRegistry<T extends Enum<T>, R extends EnumMappingTable<T>> extends MappingRegistry<R> {

		public void register(T from, T to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(from, to);
			}
		}

	}

}
