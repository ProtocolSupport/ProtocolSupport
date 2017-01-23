package protocolsupport.protocol.legacyremapper.chunk;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerByte extends ChunkTransformer {

	@Override
	protected byte[] toLegacyData0(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		byte[] data = new byte[((hasSkyLight ? 10240 : 8192) * columnsCount) + 256];
		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;
		for (int i = 0; i < columnsCount; i++) {
			ChunkSection section = sections[i];
			BlockStorageReader storage = section.blockdata;
			int blockdataacc = 0;
			for (int block = 0; block < blocksInSection; block++) {
				int blockstate = storage.getBlockState(block);
				blockstate = table.getRemap(blockstate);
				data[blockIdIndex + block] = (byte) (blockstate >> 4);
				byte blockdata = (byte) (blockstate & 0xF);
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
		if (hasBiomeData) {
			System.arraycopy(biomeData, 0, data, skyLightIndex, 256);
		}
		return data;
	}

}
