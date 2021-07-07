package protocolsupport.protocol.typeremapper.utils;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

public class SkippingTable {

	public abstract static class IntSkippingTable extends SkippingTable {

		public abstract void set(int id);

		public abstract boolean isSet(int id);

	}

	public static class ArrayBasedIntSkippingTable extends IntSkippingTable {

		protected final boolean[] table;

		public ArrayBasedIntSkippingTable(int size) {
			table = new boolean[size];
			Arrays.fill(table, false);
		}

		@Override
		public void set(int id) {
			table[id] = true;
		}

		@Override
		public boolean isSet(int id) {
			return table[id];
		}

	}

	public static class GenericSkippingTable<T> extends SkippingTable {

		protected final HashSet<T> set = new HashSet<>();

		public void set(T id) {
			set.add(id);
		}

		public boolean isSet(T id) {
			return set.contains(id);
		}

	}

	public static class EnumSkippingTable<T extends Enum<T>> extends SkippingTable {

		protected final EnumSet<T> set;

		public EnumSkippingTable(Class<T> clazz) {
			this.set = EnumSet.noneOf(clazz);
		}

		public void set(T id) {
			set.add(id);
		}

		public boolean isSet(T id) {
			return set.contains(id);
		}

	}

}
