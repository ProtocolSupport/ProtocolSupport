package protocolsupport.protocol.typeremapper.chunk;

import java.io.ByteArrayOutputStream;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class ChunkTransformerPE extends ChunkTransformer {

	private static final byte[] emptySection = new byte[4096 + 2048];

	private final byte[] blocks = new byte[4096];
	private final byte[] blockdata = new byte[2048];

	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		ByteArrayOutputStream stream = new ByteArrayOutputStream(10241 * sections.length);
		stream.write(sections.length);
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			stream.write(0); //type
			if (section != null) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						int xzoffset = (x << 7) | (z << 3);
						for (int y = 0; y < 16; y += 2) {
							int stateL = PEDataValues.BLOCK_ID.getRemap(table.getRemap(getBlockState(section, x, y, z)));
							int stateH = PEDataValues.BLOCK_ID.getRemap(table.getRemap(getBlockState(section, x, y + 1, z)));
							blocks[((xzoffset << 1) | y)] = (byte) MinecraftData.getBlockIdFromState(stateL);
							blocks[((xzoffset << 1) | (y + 1))] = (byte) MinecraftData.getBlockIdFromState(stateH);
							blockdata[(xzoffset | (y >> 1))] = (byte) ((MinecraftData.getBlockDataFromState(stateH) << 4) | MinecraftData.getBlockDataFromState(stateL));
						}
					}
				}
				stream.write(blocks, 0, blocks.length);
				stream.write(blockdata, 0, blockdata.length);
			} else {
				stream.write(emptySection, 0, emptySection.length);
			}
		}
		stream.write(new byte[512], 0, 512); //heightmap
		stream.write(biomeData, 0 , 256);
		return stream.toByteArray();
	}

	private static int getBlockState(ChunkSection section, int x, int y, int z) {
		return section.blockdata.getBlockState((y << 8) | (z << 4) | (x));
	}

}
