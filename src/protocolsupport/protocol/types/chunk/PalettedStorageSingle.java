package protocolsupport.protocol.types.chunk;

public final class PalettedStorageSingle implements IPalettedStorage {

	protected final int id;

	public PalettedStorageSingle(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public int getBitsPerNumber() {
		return 0;
	}

	@Override
	public int getId(int index) {
		return id;
	}

}
