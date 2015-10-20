package protocolsupport.protocol.typeskipper.id;

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

}
