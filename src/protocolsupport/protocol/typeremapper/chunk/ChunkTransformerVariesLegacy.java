package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkTransformerVariesLegacy extends ChunkTransformerBB {

	public ChunkTransformerVariesLegacy(ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, TileDataCache tilecache) {
		super(blockRemappingTable, tileremapper, tilecache);
	}

	protected static final int globalPaletteBitsPerBlock = 13;

	@Override
	public void writeLegacyData(ByteBuf buffer) {
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			if (section != null) {
				BlockStorageReader storage = section.blockdata;
				int bitsPerBlock = storage.getBitsPerBlock();
				if (bitsPerBlock > 8) {
					buffer.writeByte(globalPaletteBitsPerBlock);
					VarNumberSerializer.writeVarInt(buffer, 0);
					BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock, blocksInSection);
					for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
						blockstorage.setBlockState(blockIndex, PreFlatteningBlockIdData.getCombinedId(blockTypeRemappingTable.getRemap(getBlockState(i, storage, blockIndex))));
					}
					ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getBlockData());
				} else {
					buffer.writeByte(bitsPerBlock);
					BlockPalette palette = new BlockPalette();
					BlockStorageWriter blockstorage = new BlockStorageWriter(bitsPerBlock, blocksInSection);
					for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
						blockstorage.setBlockState(blockIndex, palette.getRuntimeId(PreFlatteningBlockIdData.getCombinedId(blockTypeRemappingTable.getRemap(getBlockState(i, storage, blockIndex)))));
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
				buffer.writeByte(biomeData[i]);
			}
		}
	}

}
