package protocolsupport.protocol.typeremapper.chunk;

import java.io.ByteArrayOutputStream;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class ChunkTransformerPE extends ChunkTransformer {

	private final int[] blocks = new int[2048]; //Standard palette 2048 ints.
	private final IntArrayList states = new IntArrayList();

	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		ByteArrayOutputStream stream = new ByteArrayOutputStream(10241 * sections.length);
		stream.write(sections.length);
		for (int i = 0; i < sections.length; i++) {
			states.clear();
			ChunkSection section = sections[i];
			stream.write(8); //type (1.2 multiple storage)
			stream.write((1 << 7) | 16); //Runtime id (1) and Palette 16.
			writeVarInt(stream, section == null ? 0 : 1); //blockstorage count (first is blocks second is water, we only do first for now)
			if (section != null) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						for (int y = 0; y < 16; y++) {
							int block = PEDataValues.BLOCK_ID.getRemap(table.getRemap(getBlockState(section, x, y, z)));
							if (!states.contains(block)) {
								states.add(block);
							}
							block = states.indexOf(block);
							blocks[(x << 7) | (z << 3) | (y)] = 
						}
					}
				}
				
				
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
			}
		}
		stream.write(new byte[512], 0, 512); //heightmap
		stream.write(biomeData, 0 , 256);
		return stream.toByteArray();
	}

	private static int getBlockState(ChunkSection section, int x, int y, int z) {
		return section.blockdata.getBlockState((y << 8) | (z << 4) | (x));
	}

	private static void writeVarInt(ByteArrayOutputStream to, int i) {
		while ((i & 0xFFFFFF80) != 0x0) {
			to.write(i | 0x80);
			i >>>= 7;
		}
		to.write(i);
	}

}
