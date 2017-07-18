package protocolsupport.protocol.typeremapper.id;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.GenericRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.IdRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.StringRemappingTable;
import protocolsupport.utils.Utils;

public abstract class RemappingRegistry<T extends RemappingTable> {

	private final EnumMap<ProtocolVersion, T> remappings = new EnumMap<>(ProtocolVersion.class);

	public T getTable(ProtocolVersion version) {
		return Utils.getFromMapOrCreateDefault(remappings, version, createTable());
	}

	protected abstract T createTable();

	public static abstract class IdRemappingRegistry<T extends IdRemappingTable> extends RemappingRegistry<T> {

		public void registerRemapEntry(int from, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}
	
	public static abstract class StringRemappingRegistry<T extends StringRemappingTable> extends RemappingRegistry<T> {

		public void registerRemapEntry(String from, String to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

	public static abstract class EnumRemappingRegistry<T extends Enum<T>, R extends EnumRemappingTable<T>> extends RemappingRegistry<R> {

		public void registerRemapEntry(T from, T to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

	public static abstract class GenericRemappingRegistry<T, R extends GenericRemappingTable<T>> extends RemappingRegistry<R> {

		public void registerRemapEntry(T from, T to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).setRemap(from, to);
			}
		}

	}

}
