package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerVaries extends ChunkTransformerBB {

	protected final FlatteningBlockDataTable flatteningBlockDataTable;
	public ChunkTransformerVaries(ArrayBasedIdRemappingTable blockTypeRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, TileEntityRemapper tileremapper, TileDataCache tilecache) {
		super(blockTypeRemappingTable, tileremapper, tilecache);
		this.flatteningBlockDataTable = flatteningBlockDataTable;
	}

	protected static final int globalPaletteBitsPerBlock = 14;

	@Override
	public void writeLegacyData(ByteBuf buffer) {
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			if (section != null) {
				BlockStorageReader storage = section.blockdata;

				int bitsPerBlock = storage.getBitsPerBlock();
				if (bitsPerBlock > 8) {
					buffer.writeByte(globalPaletteBitsPerBlock);
					BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock, blocksInSection);
					for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
						int blockdata = storage.getBlockData(blockIndex);
						blockstorage.setBlockState(blockIndex, BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockdata));
					}
					ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getBlockData());
				} else {
					buffer.writeByte(bitsPerBlock);
					BlockPalette palette = new BlockPalette();
					BlockStorageWriter blockstorage = new BlockStorageWriter(bitsPerBlock, blocksInSection);
					for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
						int blockdata = storage.getBlockData(blockIndex);
						blockstorage.setBlockState(blockIndex, palette.getRuntimeId(BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockdata)));
					}
					ArraySerializer.writeVarIntVarIntArray(buffer, palette.getBlockStates());
					ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getBlockData());
				}

				buffer.writeBytes(section.blocklight);
				if (hasSkyLight) {
					buffer.writeBytes(section.skylight);
				}
			}
		}

		if (hasBiomeData) {
			for (int i = 0; i < biomeData.length; i++) {
				buffer.writeInt(biomeData[i]);
			}
		}
	}

}
