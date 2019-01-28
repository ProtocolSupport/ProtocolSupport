package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerShort extends ChunkTransformerBA {

	public ChunkTransformerShort(ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, TileDataCache tilecache) {
		super(blockRemappingTable, tileremapper, tilecache);
	}

	@Override
	public byte[] toLegacyData() {
		byte[] data = new byte[((hasSkyLight ? 12288 : 10240) * columnsCount) + 256];
		int blockIdIndex = 0;
		int blockLightIndex = 8192 * columnsCount;
		int skyLightIndex = 10240 * columnsCount;
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			if (section != null) {
				BlockStorageReader storage = section.blockdata;
				for (int block = 0; block < blocksInSection; block++) {
					int dataindex = blockIdIndex + (block << 1);
					int blockstate = getBlockState(i, storage, block);
					blockstate = PreFlatteningBlockIdData.getCombinedId(blockTypeRemappingTable.getRemap(blockstate));
					data[dataindex] = (byte) blockstate;
					data[dataindex + 1] = (byte) (blockstate >> 8);
				}
				blockIdIndex += 8192;
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
