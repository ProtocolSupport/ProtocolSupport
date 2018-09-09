package protocolsupport.protocol.typeremapper.utils;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.GenericRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.IdRemappingTable;

public abstract class RemappingRegistry<T extends RemappingTable> {

	protected final EnumMap<ProtocolVersion, T> remappings = new EnumMap<>(ProtocolVersion.class);

	public T getTable(ProtocolVersion version) {
		return remappings.computeIfAbsent(version, k -> createTable());
	}

	protected abstract T createTable();

	public abstract static class IdRemappingRegistry<T extends IdRemappingTable> extends RemappingRegistry<T> {

		public void registerRemapEntry(int from, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

	public abstract static class EnumRemappingRegistry<T extends Enum<T>, R extends EnumRemappingTable<T>> extends RemappingRegistry<R> {

		public void registerRemapEntry(T from, T to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

	public abstract static class GenericRemappingRegistry<T, R extends GenericRemappingTable<T>> extends RemappingRegistry<R> {

		public void registerRemapEntry(T from, T to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

}
