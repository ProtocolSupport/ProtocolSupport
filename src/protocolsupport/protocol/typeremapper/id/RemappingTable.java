package protocolsupport.protocol.typeremapper.id;

import java.util.HashMap;

public class RemappingTable {

	protected final int[] table;
	public RemappingTable(int size) {
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

	public static class HashRemappingTable extends RemappingTable {

		public HashRemappingTable() {
			super(0);
		}

		protected final HashMap<Integer, Integer> table = new HashMap<>();

		public void setRemap(int from, int to) {
			table.put(from, to);
		}

		public int getRemap(int id) {
			Integer r = table.get(id);
			return r != null ? r : id;
		}
		
	}

}
