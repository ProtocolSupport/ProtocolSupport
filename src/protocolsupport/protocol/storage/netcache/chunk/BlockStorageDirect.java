package protocolsupport.protocol.storage.netcache.chunk;

import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public class BlockStorageDirect extends NumberBitsStoragePadded implements BlockStorage {

	public BlockStorageDirect() {
		super(ChunkConstants.PALETTED_STORAGE_BLOCKS_GLOBAL_BITS, ChunkConstants.SECTION_BLOCK_COUNT);
	}

	public BlockStorageDirect(NumberBitsStoragePadded other) {
		super(other.getBitsPerNumber(), other.getStorage());
	}

	@Override
	public short getBlockData(int blockIndex) {
		return (short) getNumber(blockIndex);
	}

	@Override
	public void setBlockData(int blockIndex, short blockstate) {
		setNumber(blockIndex, blockstate);
	}

}