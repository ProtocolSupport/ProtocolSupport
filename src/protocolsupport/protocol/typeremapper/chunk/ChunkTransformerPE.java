package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerPE extends ChunkTransformerBB {

	public ChunkTransformerPE(ArrayBasedIdRemappingTable blockRemappingTable) {
		super(blockRemappingTable);
	}

	protected static final int flag_runtime = 1;

	//we always use 2 bytes so every block id fits, and we don't have to use that retarded block storage
	protected static final int bitsPerBlock = 16;

	@Override
	public void writeLegacyData(ByteBuf chunkdata) {
		chunkdata.writeByte(sections.length);
		for (int i = 0; i < sections.length; i++) {
			chunkdata.writeByte(1); //subchunk version
			ChunkSection section = sections[i];
			if (section != null) {
				BlockPalette palette = new BlockPalette();
				chunkdata.writeByte((bitsPerBlock << 1) | flag_runtime);
				for (int x = 0; x < 16 ; x++) {
					for (int z = 0; z < 16; z++) {
						for (int y = 0; y < 16; y++) {
							chunkdata.writeShortLE(palette.getRuntimeId(getBlockState(section, x, y, z)));
						}
					}
				}
				int[] blockstates = palette.getBlockStates();
				VarNumberSerializer.writeSVarInt(chunkdata, blockstates.length);
				for (int blockstate : blockstates) {
					VarNumberSerializer.writeSVarInt(chunkdata, blockstate);
				}
			} else {
				chunkdata.writeByte((1 << 1) | flag_runtime);
				chunkdata.writeZero(512);
				VarNumberSerializer.writeSVarInt(chunkdata, 1);
				VarNumberSerializer.writeSVarInt(chunkdata, 0);
			}
		}
		chunkdata.writeZero(512); //heightmap (will be recalculated by client anyway)
		for (int i = 0; i < biomeData.length; i++) {
			chunkdata.writeByte(biomeData[i]);
		}
	}

	protected static int getBlockState(ChunkSection section, int x, int y, int z) {
		return section.blockdata.getBlockState((y << 8) | (z << 4) | (x));
	}

}
