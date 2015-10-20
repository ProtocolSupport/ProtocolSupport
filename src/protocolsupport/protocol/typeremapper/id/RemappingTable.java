package protocolsupport.protocol.typeremapper.id;

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

}
