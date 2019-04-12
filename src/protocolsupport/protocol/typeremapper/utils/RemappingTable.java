package protocolsupport.protocol.typeremapper.utils;

import java.util.EnumMap;
import java.util.HashMap;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class RemappingTable {

	public abstract static class IdRemappingTable extends RemappingTable {

		public abstract void setRemap(int from, int to);

		public abstract int getRemap(int id);

	}

	public static class ArrayBasedIdRemappingTable extends IdRemappingTable {

		protected final int[] table;
		public ArrayBasedIdRemappingTable(int size) {
			table = new int[size];
			for (int i = 0; i < table.length; i++) {
				table[i] = i;
			}
		}

		@Override
		public void setRemap(int from, int to) {
			table[from] = to;
		}

		@Override
		public int getRemap(int id) {
			if ((id >= 0) && (id < table.length)) {
				return table[id];
			} else {
				return id;
			}
		}

	}

	public static class HashMapBasedIdRemappingTable extends IdRemappingTable {

		protected final Int2IntOpenHashMap table = new Int2IntOpenHashMap();
		{
			table.defaultReturnValue(-1);
		}

		@Override
		public void setRemap(int from, int to) {
			table.put(from, to);
		}

		@Override
		public int getRemap(int id) {
			int r = table.get(id);
			return r != table.defaultReturnValue() ? r : id;
		}

	}

	public static class EnumRemappingTable<T extends Enum<T>> extends RemappingTable {

		protected final EnumMap<T, T> table;
		public EnumRemappingTable(Class<T> enumClazz) {
			table = new EnumMap<>(enumClazz);
		}

		public void setRemap(T from, T to) {
			table.put(from, to);
		}

		public T getRemap(T from) {
			return table.getOrDefault(from, from);
		}

	}

	public static class GenericRemappingTable<T> extends RemappingTable {

		protected final HashMap<T, T> table = new HashMap<>();

		public void setRemap(T from, T to) {
			table.put(from, to);
		}

		public T getRemap(T from) {
			return table.getOrDefault(from, from);
		}

		public boolean hasRemap(T from) {
			return table.get(from) != null;
		}
	}

}
