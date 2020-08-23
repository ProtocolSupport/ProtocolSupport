package protocolsupport.protocol.typeremapper.utils;

import java.util.EnumMap;
import java.util.HashMap;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class MappingTable {

	public abstract static class IdMappingTable extends MappingTable {

		public abstract void set(int from, int to);

		public abstract int get(int id);

	}

	public static class ArrayBasedIntMappingTable extends IdMappingTable {

		protected final int[] table;

		public ArrayBasedIntMappingTable(int size) {
			table = new int[size];
			for (int i = 0; i < table.length; i++) {
				table[i] = i;
			}
		}

		@Override
		public void set(int from, int to) {
			table[from] = to;
		}

		@Override
		public int get(int id) {
			if ((id >= 0) && (id < table.length)) {
				return table[id];
			} else {
				return id;
			}
		}

	}

	public static class HashMapBasedIntMappingTable extends IdMappingTable {

		protected final Int2IntOpenHashMap table = new Int2IntOpenHashMap();
		{
			table.defaultReturnValue(-1);
		}

		@Override
		public void set(int from, int to) {
			table.put(from, to);
		}

		@Override
		public int get(int id) {
			int r = table.get(id);
			return r != table.defaultReturnValue() ? r : id;
		}

	}

	public static class GenericMappingTable<T> extends MappingTable {

		protected final HashMap<T, T> table = new HashMap<>();

		public void set(T from, T to) {
			table.put(from, to);
		}

		public T get(T from) {
			return table.getOrDefault(from, from);
		}

	}

	public static class EnumMappingTable<T extends Enum<T>> extends MappingTable {

		protected final EnumMap<T, T> table;

		public EnumMappingTable(Class<T> clazz) {
			this.table = new EnumMap<>(clazz);
		}

		public void set(T from, T to) {
			table.put(from, to);
		}

		public T get(T from) {
			return table.getOrDefault(from, from);
		}

	}

}
