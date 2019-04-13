package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerPE extends ChunkTransformerBB {

	public ChunkTransformerPE(ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, TileDataCache tilecache, ArrayBasedIdRemappingTable biomeRemappingTable) {
		super(blockRemappingTable, tileremapper, tilecache);
		this.biomeRemappingTable = biomeRemappingTable;
	}

	protected final ArrayBasedIdRemappingTable biomeRemappingTable;
	protected static final int flag_runtime = 1;

	@Override
	public void writeLegacyData(ByteBuf chunkdata) {
		chunkdata.writeByte(sections.length);
		for (int i = 0; i < sections.length; i++) {
			chunkdata.writeByte(8); //subchunk version
			ChunkSection section = sections[i];
			if (section != null) {
				chunkdata.writeByte(2); //blockstorage count.
				BlockStorageReader storage = section.blockdata;
				BlockPalette palette = new BlockPalette();
				int bitsPerBlock = getPocketBitsPerBlock(storage.getBitsPerBlock());
				BlockStorageWriterPE blockstorage = new BlockStorageWriterPE(bitsPerBlock, blocksInSection);
				BlockStorageWriterPE waterstorage = new BlockStorageWriterPE(1, blocksInSection); //Waterlogged -> second storage. Only true/false per block
				chunkdata.writeByte((bitsPerBlock << 1) | flag_runtime);

				int peIndex = 0;
				for (int x = 0; x < 16; x++) { for (int z = 0; z < 16; z++) { for (int y = 0; y < 16; y++) {
					int pcIndex = getPcIndex(x, y, z);
					int blockstate = storage.getBlockData(pcIndex);
					// Update tile entity cache
					processBlockData(i, pcIndex, blockstate);

					// Remap to PE blockstate
					if (PEBlocks.isPCBlockWaterlogged(blockstate)) { waterstorage.setBlockState(peIndex, 1); }
					blockstorage.setBlockState(peIndex, palette.getRuntimeId(PEBlocks.getPocketRuntimeId(blockDataRemappingTable.getRemap(blockstate))));
					peIndex++;
				}}}

				for (int word : blockstorage.getBlockData()) {
					chunkdata.writeIntLE(word);
				}
				ArraySerializer.writeSVarIntSVarIntArray(chunkdata, palette.getBlockStates());
				chunkdata.writeByte((1 << 1) | flag_runtime); //Water storage.
				for (int word : waterstorage.getBlockData()) {
					chunkdata.writeIntLE(word);
				}
				VarNumberSerializer.writeSVarInt(chunkdata, 2); //Palette size
				VarNumberSerializer.writeSVarInt(chunkdata, 0); //Palette air
				VarNumberSerializer.writeSVarInt(chunkdata, 54); //Palette water
			} else {
				chunkdata.writeByte(1); //blockstorage count.
				chunkdata.writeByte((1 << 1) | flag_runtime);
				chunkdata.writeZero(512);
				VarNumberSerializer.writeSVarInt(chunkdata, 1); //Palette size
				VarNumberSerializer.writeSVarInt(chunkdata, 0); //Palette: Air
			}
		}
		chunkdata.writeZero(512); //heightmap (will be recalculated by client anyway)
		for (int i = 0; i < biomeData.length; i++) {
			chunkdata.writeByte(biomeRemappingTable.getRemap(biomeData[i]));
		}
	}

	protected static int getPcIndex(int x, int y, int z) {
		return (y << 8) | (z << 4) | (x);
	}

	protected static int getPocketBitsPerBlock(int pcBitsPerBlock) {
		if (pcBitsPerBlock == 7) {
			return 8;
		} else if (pcBitsPerBlock > 8) {
			return 16;
		}
		return pcBitsPerBlock;
	}

}
