package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerByte extends ChunkTransformerBA {

	public ChunkTransformerByte(ArrayBasedIdRemappingTable blockDataRemappingTable, TileEntityRemapper tileRemapper, TileDataCache tileCache) {
		super(blockDataRemappingTable, tileRemapper, tileCache);
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

				int blockLegacyDataAcc = 0;
				for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
					int blockdata = storage.getBlockData(blockIndex);
					processBlockData(i, blockIndex, blockdata);

					blockdata = BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, blockdata);
					data[blockIdIndex + blockIndex] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockdata);
					byte blockLegacyData = (byte) PreFlatteningBlockIdData.getDataFromCombinedId(blockdata);
					if ((blockIndex & 1) == 0) {
						blockLegacyDataAcc = blockLegacyData;
					} else {
						blockLegacyDataAcc |= (blockLegacyData << 4);
						data[(blockIndex >> 1) + blockDataIndex] = (byte) blockLegacyDataAcc;
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
