package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public class ChunkWriterVariesWithLight {

	public static void writeSectionsFlattening(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (Utils.isBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					buffer.writeByte(globalPaletteBitsPerBlock);
					BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock);
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

				sectionPresentConsumer.accept(sectionNumber);
			}
		}
	}


	public static void writeSectionsPreFlattening(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (Utils.isBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					buffer.writeByte(globalPaletteBitsPerBlock);
					VarNumberSerializer.writeVarInt(buffer, 0);
					BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock);
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

				sectionPresentConsumer.accept(sectionNumber);
			}
		}
	}

}
