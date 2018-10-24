package protocolsupport.protocol.typeremapper.utils;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.IntSkippingTable;

public abstract class SkippingRegistry<T extends SkippingTable> {

	private final EnumMap<ProtocolVersion, T> registry = new EnumMap<>(ProtocolVersion.class);

	public T getTable(ProtocolVersion version) {
		return registry.computeIfAbsent(version, k -> createTable());
	}

	protected abstract T createTable();

	public abstract static class IntSkippingRegistry<T extends IntSkippingTable> extends SkippingRegistry<T> {

		public void registerSkipEntry(int id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setSkip(id);
			}
		}

	}

	public abstract static class EnumSkippingRegistry<T extends Enum<T>, R extends EnumSkippingTable<T>> extends SkippingRegistry<R> {

		public void registerSkipEntry(T id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setSkip(id);
			}
		}

	}

	public abstract static class GenericSkippingRegistry<T, R extends GenericSkippingTable<T>> extends SkippingRegistry<R> {

		public void registerSkipEntry(T id, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setSkip(id);
			}
		}

	}

}
