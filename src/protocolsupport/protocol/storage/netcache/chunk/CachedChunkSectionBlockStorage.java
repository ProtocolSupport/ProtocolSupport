package protocolsupport.protocol.storage.netcache.chunk;

import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;

//TODO: get rid of block count after implementing chunk cache only for <= 1.13
public class CachedChunkSectionBlockStorage {

	protected int blockCount;
	protected BlockStorage storage;

	public CachedChunkSectionBlockStorage() {
		storage = new StoragePaletted();
	}

	public CachedChunkSectionBlockStorage(int blockCount, ChunkSectonBlockData reader) {
		this.blockCount = blockCount;
		if (reader.getBitsPerBlock() == ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
			storage = new BlockStorageDirect(reader.getBlockData());
		} else {
			StoragePaletted storagePaletted = new StoragePaletted(reader.getPalette());
			for (int index = 0; index < ChunkConstants.BLOCKS_IN_SECTION; index++) {
				storagePaletted.setRuntimeId(index, (byte) reader.getRuntimeId(index));
			}
			storage = storagePaletted;
		}
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public int getBlockData(int index) {
		return storage.getBlockData(index);
	}

	public void setBlockData(int index, short blockdata) {
		try {
			storage.setBlockData(index, blockdata);
		} catch (StoragePaletted.MaxSizeReachedException e) {
			BlockStorageDirect storageDirect = new BlockStorageDirect();
			for (int lindex = 0; lindex < ChunkConstants.BLOCKS_IN_SECTION; lindex++) {
				storageDirect.setBlockData(lindex, storage.getBlockData(lindex));
			}
			storageDirect.setBlockData(index, blockdata);
			storage = storageDirect;
		}
	}

}
