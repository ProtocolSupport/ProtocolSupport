package protocolsupport.protocol.watchedentity.remapper;

public class RemappingEntry {

	private int from;
	private int to;
	private ValueRemapper vremap = ValueRemapper.NO_OP;

	protected RemappingEntry(int from, int to) {
		this.from = from;
		this.to = to;
	}

	protected RemappingEntry(int from, int to, ValueRemapper vremap) {
		this(from, to);
		this.vremap = vremap;
	}

	public int getIdFrom() {
		return from;
	}

	public int getIdTo() {
		return to;
	}

	public ValueRemapper getValueRemapper() {
		return vremap;
	}

}