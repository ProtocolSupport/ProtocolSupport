package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTLongArray;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public class HeightMapTransformer {

	protected static final int ENTRY_BITS_SIZE = 9;
	protected static final int ENTRY_COUNT = 256;

	public static long[] transformStorageToCompact(long[] storage) {
		NumberBitsStoragePadded storagePadded = new NumberBitsStoragePadded(ENTRY_BITS_SIZE, storage);
		NumberBitsStorageCompact storageCompact = new NumberBitsStorageCompact(ENTRY_BITS_SIZE, ENTRY_COUNT);
		for (int i = 0; i < ENTRY_COUNT; i++) {
			storageCompact.setNumber(i, storagePadded.getNumber(i));
		}
		return storageCompact.getStorage();
	}

	protected static final String NAME_MOTION_BLOCKING = "MOTION_BLOCKING";
	protected static final String NAME_WORLD_SURFACE = "WORLD_SURFACE";

	public static NBTCompound transform(NBTCompound heightmaps) {
		heightmaps.setTag(NAME_MOTION_BLOCKING, new NBTLongArray(transformStorageToCompact(heightmaps.getTagOfType(NAME_MOTION_BLOCKING, NBTType.LONG_ARRAY).getValue())));
		heightmaps.setTag(NAME_WORLD_SURFACE, new NBTLongArray(transformStorageToCompact(heightmaps.getTagOfType(NAME_WORLD_SURFACE, NBTType.LONG_ARRAY).getValue())));
		return heightmaps;
	}

}
