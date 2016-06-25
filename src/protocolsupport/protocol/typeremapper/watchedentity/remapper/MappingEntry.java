package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;

public class MappingEntry {

	protected final int from;
	protected final int to;
	protected final ValueRemapper<?> vremap;

	protected MappingEntry(int from, int to, ValueRemapper<?> vremap) {
		this.from = from;
		this.to = to;
		this.vremap = vremap;
	}

	public int getIdFrom() {
		return from;
	}

	public int getIdTo() {
		return to;
	}

	@SuppressWarnings("rawtypes")
	public ValueRemapper getValueRemapper() {
		return vremap;
	}

}