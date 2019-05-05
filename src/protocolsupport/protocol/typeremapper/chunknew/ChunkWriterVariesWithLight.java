package protocolsupport.protocol.typeremapper.chunknew;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.chunk.BlocksSection;
import protocolsupport.protocol.utils.chunk.ChunkConstants;

public class ChunkWriterVariesWithLight {

	public static void writeSectionDataFlattening(
		ByteBuf buffer, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		CachedChunk chunk, boolean hasSkyLight, int sectionNumber
	) {
		BlocksSection section = chunk.getBlocksSection(sectionNumber);

		if (section != null) {
			buffer.writeByte(globalPaletteBitsPerBlock);
			BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
			for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
				blockstorage.setBlockState(blockIndex, BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
			}
			ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getBlockData());
		} else {
			ChunkUtils.writeBBEmptySection(buffer);
		}

		ChunkUtils.writeBBLight(buffer, chunk.getBlockLight(sectionNumber));
		if (hasSkyLight) {
			ChunkUtils.writeBBLight(buffer, chunk.getSkyLight(sectionNumber));
		}
	}


	public static void writeSectionDataPreFlattening(
		ByteBuf buffer, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight, int sectionNumber
	) {
		BlocksSection section = chunk.getBlocksSection(sectionNumber);

		if (section != null) {
			buffer.writeByte(globalPaletteBitsPerBlock);
			VarNumberSerializer.writeVarInt(buffer, 0);
			BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
			for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
				blockstorage.setBlockState(blockIndex, BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex)));
			}
			ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getBlockData());
		} else {
			ChunkUtils.writeBBEmptySection(buffer);
		}

		ChunkUtils.writeBBLight(buffer, chunk.getBlockLight(sectionNumber));
		if (hasSkyLight) {
			ChunkUtils.writeBBLight(buffer, chunk.getSkyLight(sectionNumber));
		}
	}

}
