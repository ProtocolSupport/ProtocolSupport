package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;

public class MappingEntry {

	protected int from;
	protected int to;
	protected ValueRemapper<?> vremap = ValueRemapper.NO_OP;

	protected MappingEntry(int from, int to) {
		this.from = from;
		this.to = to;
	}

	protected MappingEntry(int from, int to, ValueRemapper<?> vremap) {
		this(from, to);
		this.vremap = vremap;
	}

	protected MappingEntry(int id, ValueRemapper<?> vremap) {
		this(id, id, vremap);
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

	public static class MappingEntryOriginal extends MappingEntry {
		public MappingEntryOriginal(int id) {
			super(id, id);
		}

		public static MappingEntryOriginal[] of(int... ids) {
			MappingEntryOriginal[] entries = new MappingEntryOriginal[ids.length];
			for (int i = 0; i < entries.length; i++) {
				entries[i] = new MappingEntryOriginal(ids[i]);
			}
			return entries;
		}
	}

}