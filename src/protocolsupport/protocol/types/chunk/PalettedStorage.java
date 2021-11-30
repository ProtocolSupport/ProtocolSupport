package protocolsupport.protocol.types.chunk;

import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public final class PalettedStorage extends NumberBitsStoragePadded implements IPalettedStorage {

	protected final short[] palette;

	public PalettedStorage(short[] palette, int bitsPerNumber, long[] blockdata) {
		super(bitsPerNumber, blockdata);
		this.palette = palette;
	}

	public short[] getPalette() {
		return palette;
	}

	@Override
	public int getId(int index) {
		return palette[getNumber(index)];
	}

}
