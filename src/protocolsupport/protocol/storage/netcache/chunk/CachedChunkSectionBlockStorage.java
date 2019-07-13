package protocolsupport.protocol.storage.netcache.chunk;

import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;

public class CachedChunkSectionBlockStorage {

	protected BlockStorage storage;

	public CachedChunkSectionBlockStorage() {
		storage = new BlockStoragePaletted();
	}

	public CachedChunkSectionBlockStorage(ChunkSectonBlockData reader) {
		if (reader.getBitsPerBlock() == ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
			storage = new BlockStorageDirect(reader.getBlockData());
		} else {
			BlockStoragePaletted storagePaletted = new BlockStoragePaletted(reader.getPalette());
			for (int index = 0; index < ChunkConstants.BLOCKS_IN_SECTION; index++) {
				storagePaletted.setRuntimeId(index, (byte) reader.getRuntimeId(index));
			}
			storage = storagePaletted;
		}
	}

	public int getBlockData(int index) {
		return storage.getBlockData(index);
	}

	public void setBlockData(int index, short blockdata) {
		try {
			storage.setBlockData(index, blockdata);
		} catch (BlockStoragePaletted.MaxSizeReachedException e) {
			BlockStorageDirect storageDirect = new BlockStorageDirect();
			for (int lindex = 0; lindex < ChunkConstants.BLOCKS_IN_SECTION; lindex++) {
				storageDirect.setBlockData(lindex, storage.getBlockData(lindex));
			}
			storageDirect.setBlockData(index, blockdata);
			storage = storageDirect;
		}
	}

}
