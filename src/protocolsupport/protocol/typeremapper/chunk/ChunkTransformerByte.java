package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerByte extends ChunkTransformerBA {

	public ChunkTransformerByte(ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, TileDataCache tilecache) {
		super(blockRemappingTable, tileremapper, tilecache);
	}

	@Override
	public byte[] toLegacyData() {
		byte[] data = new byte[((hasSkyLight ? 10240 : 8192) * columnsCount) + 256];
		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			if (section != null) {
				BlockStorageReader storage = section.blockdata;
				int blockdataacc = 0;
				for (int block = 0; block < blocksInSection; block++) {
					int blockstate = PreFlatteningBlockIdData.getCombinedId(blockTypeRemappingTable.getRemap(getBlockState(i, storage, block)));
					data[blockIdIndex + block] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockstate);
					byte blockdata = (byte) PreFlatteningBlockIdData.getDataFromCombinedId(blockstate);
					if ((block & 1) == 0) {
						blockdataacc = blockdata;
					} else {
						blockdataacc |= (blockdata << 4);
						data[(block >> 1) + blockDataIndex] = (byte) blockdataacc;
					}
				}
				blockIdIndex += 4096;
				blockDataIndex += 2048;
				System.arraycopy(section.blocklight, 0, data, blockLightIndex, 2048);
				blockLightIndex += 2048;
				if (hasSkyLight) {
					System.arraycopy(section.skylight, 0, data, skyLightIndex, 2048);
					skyLightIndex += 2048;
				}
			}
		}
		if (hasBiomeData) {
			for (int i = 0; i < biomeData.length; i++) {
				data[skyLightIndex + i] = (byte) biomeData[i];
			}
		}
		return data;
	}

}
