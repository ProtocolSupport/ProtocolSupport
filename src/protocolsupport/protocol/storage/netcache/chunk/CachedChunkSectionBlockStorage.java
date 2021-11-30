package protocolsupport.protocol.storage.netcache.chunk;

import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.IPalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorageGlobal;
import protocolsupport.protocol.types.chunk.PalettedStorageSingle;

public class CachedChunkSectionBlockStorage {

	protected BlockStorage storage;

	public CachedChunkSectionBlockStorage() {
		storage = new BlockStorageBytePaletted();
	}

	public CachedChunkSectionBlockStorage(IPalettedStorage storage) {
		if (storage instanceof PalettedStorageGlobal) {
			this.storage = new BlockStorageDirect();
		} else if (storage instanceof PalettedStorage palettedStorage) {
			BlockStorageBytePaletted storagePaletted = new BlockStorageBytePaletted(palettedStorage.getPalette());
			for (int index = 0; index < ChunkConstants.SECTION_BLOCK_COUNT; index++) {
				storagePaletted.setRuntimeId(index, (byte) palettedStorage.getNumber(index));
			}
			this.storage = storagePaletted;
		} else if (storage instanceof PalettedStorageSingle palettedStorageSingle) {
			this.storage = new BlockStorageBytePaletted(new short[] {(short) palettedStorageSingle.getId()});
		}
	}

	public BlockStorage getBlockStorage() {
		return storage;
	}

	public int getBlockData(int index) {
		return storage.getBlockData(index);
	}

	public void setBlockData(int index, short blockdata) {
		try {
			storage.setBlockData(index, blockdata);
		} catch (BlockStorageBytePaletted.MaxSizeReachedException e) {
			BlockStorageDirect storageDirect = new BlockStorageDirect();
			for (int lindex = 0; lindex < ChunkConstants.SECTION_BLOCK_COUNT; lindex++) {
				storageDirect.setBlockData(lindex, storage.getBlockData(lindex));
			}
			storageDirect.setBlockData(index, blockdata);
			storage = storageDirect;
		}
	}

}
