package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerShort extends ChunkTransformer {

	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(ProtocolVersion.MINECRAFT_1_8);
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
					int blockstate = storage.getBlockState(block);
					blockstate = table.getRemap(blockstate);
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
			System.arraycopy(biomeData, 0, data, skyLightIndex, 256);
		}
		return data;
	}

}
