package protocolsupport.protocol.types.chunk;

import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public final class PalettedStorageGlobal extends NumberBitsStoragePadded implements IPalettedStorage {

	protected PalettedStorageGlobal(int globalBitsPerNumber, long[] blockdata) {
		super(globalBitsPerNumber, blockdata);
	}

	@Override
	public int getId(int index) {
		return getNumber(index);
	}

}
