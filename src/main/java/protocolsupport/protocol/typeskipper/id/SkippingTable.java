package protocolsupport.protocol.typeskipper.id;

import gnu.trove.map.hash.TIntByteHashMap;

public class SkippingTable {

	protected final boolean[] table;
	public SkippingTable(int size) {
		table = new boolean[size];
		for (int i = 0; i < table.length; i++) {
			table[i] = false;
		}
	}

	public void setSkip(int id) {
		table[id] = true;
	}

	public boolean shouldSkip(int id) {
		return table[id];
	}

	public static class HashSkippingTable extends SkippingTable {

		public HashSkippingTable() {
			super(0);
		}

		protected final TIntByteHashMap table = new TIntByteHashMap(16, 0.75F);

		public void setSkip(int id) {
			table.put(id, (byte) 1);
		}

		public boolean shouldSkip(int id) {
			return table.containsKey(id);
		}
		
	}

}
