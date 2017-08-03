package protocolsupport.protocol.typeremapper.utils;

import java.util.EnumSet;
import java.util.HashSet;

import gnu.trove.map.hash.TIntByteHashMap;

public class SkippingTable {

	public static abstract class IntSkippingTable extends SkippingTable {

		public abstract void setSkip(int id);

		public abstract boolean shouldSkip(int id);

	}

	public static class ArrayBasedIntSkippingTable extends IntSkippingTable {

		protected final boolean[] table;
		public ArrayBasedIntSkippingTable(int size) {
			table = new boolean[size];
			for (int i = 0; i < table.length; i++) {
				table[i] = false;
			}
		}

		@Override
		public void setSkip(int id) {
			table[id] = true;
		}

		@Override
		public boolean shouldSkip(int id) {
			return table[id];
		}

	}

	public static class HashMapBasedIntSkippingTable extends IntSkippingTable {

		protected final TIntByteHashMap table = new TIntByteHashMap(16, 0.75F);

		@Override
		public void setSkip(int id) {
			table.put(id, (byte) 1);
		}

		@Override
		public boolean shouldSkip(int id) {
			return table.containsKey(id);
		}

	}

	public static class GenericSkippingTable<T> extends SkippingTable {

		protected final HashSet<T> set = new HashSet<>();

		public void setSkip(T id) {
			set.add(id);
		}

		public boolean shouldSkip(T id) {
			return set.contains(id);
		}

	}

	public static class EnumSkippingTable<T extends Enum<T>> extends SkippingTable {

		protected final EnumSet<T> set;
		public EnumSkippingTable(Class<T> clazz) {
			this.set = EnumSet.noneOf(clazz);
		}

		public void setSkip(T id) {
			set.add(id);
		}

		public boolean shouldSkip(T id) {
			return set.contains(id);
		}

	}

}
