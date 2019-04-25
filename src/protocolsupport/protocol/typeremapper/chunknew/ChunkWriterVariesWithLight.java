package protocolsupport.protocol.typeremapper.chunknew;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.chunk.ChunkConstants;
import protocolsupport.protocol.utils.chunk.BlocksSection;

public class ChunkWriterVariesWithLight {

	protected static final int globalPaletteBitsPerBlock = 14;

	public static void writeSectionData(
		ByteBuf buffer,
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

}
