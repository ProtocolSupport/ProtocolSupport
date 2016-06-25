package protocolsupport.protocol.typeremapper.id;

import gnu.trove.map.hash.TIntIntHashMap;

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

		public void setRemap(int from, int to) {
			table[from] = to;
		}

		public int getRemap(int id) {
			return table[id];
		}

	}

	public static class HashMapBasedIdRemappingTable extends IdRemappingTable {

		protected final TIntIntHashMap table = new TIntIntHashMap(16, 0.75F, -1, -1);

		public void setRemap(int from, int to) {
			table.put(from, to);
		}

		public int getRemap(int id) {
			int r = table.get(id);
			return r != -1 ? r : id;
		}

	}

}
