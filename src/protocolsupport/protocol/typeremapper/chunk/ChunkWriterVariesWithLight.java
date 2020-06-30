package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.chunk.BlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.BlockStorageBytePaletted;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;
import protocolsupport.utils.BitUtils;

public class ChunkWriterVariesWithLight {

	public static void writeSectionsCompactFlattening(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					BlockStorage blockstorage = section.getBlockStorage();
					if (blockstorage instanceof BlockStorageBytePaletted) {
						BlockStorageBytePaletted blockstoragePaletted = (BlockStorageBytePaletted) blockstorage;
						buffer.writeByte(8);
						BlockStorageBytePaletted.Palette paletteObject = blockstoragePaletted.getPalette();
						short[] palette = paletteObject.getPalette();
						int paletteSize = Math.min(palette.length, paletteObject.getPaletteSize());
						VarNumberSerializer.writeVarInt(buffer, paletteSize);
						for (int i = 0; i < paletteSize; i++) {
							VarNumberSerializer.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, palette[i]));
						}
						int blockdataLength = ChunkConstants.BLOCKS_IN_SECTION >> 3;
						VarNumberSerializer.writeVarInt(buffer, blockdataLength);
						for (int i = 0; i < blockdataLength; i++) {
							for (int j = 1; j <= Long.BYTES; j++) {
								buffer.writeByte(blockstoragePaletted.getRuntimeId((i << 3) + (Long.BYTES - j)));
							}
						}
					} else {
						buffer.writeByte(globalPaletteBitsPerBlock);
						VarNumberSerializer.writeVarInt(buffer, 0);
						NumberBitsStorageCompact writer = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
						for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
							writer.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
						}
						ArraySerializer.writeVarIntLongArray(buffer, writer.getStorage());
					}
				} else {
					ChunkWriterUtils.writeBBEmptySection(buffer);
				}

				ChunkWriterUtils.writeBBLight(buffer, chunk.getBlockLight(sectionNumber));
				if (hasSkyLight) {
					ChunkWriterUtils.writeBBLight(buffer, chunk.getSkyLight(sectionNumber));
				}

				sectionPresentConsumer.accept(sectionNumber);
			}
		}
	}


	public static void writeSectionsCompactPreFlattening(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionNumber);

				if (section != null) {
					BlockStorage blockstorage = section.getBlockStorage();
					if (blockstorage instanceof BlockStorageBytePaletted) {
						BlockStorageBytePaletted blockstoragePaletted = (BlockStorageBytePaletted) blockstorage;
						buffer.writeByte(Byte.SIZE);
						BlockStorageBytePaletted.Palette paletteObject = blockstoragePaletted.getPalette();
						short[] palette = paletteObject.getPalette();
						int paletteSize = Math.min(palette.length, paletteObject.getPaletteSize());
						VarNumberSerializer.writeVarInt(buffer, paletteSize);
						for (int i = 0; i < paletteSize; i++) {
							VarNumberSerializer.writeVarInt(buffer, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, palette[i]));
						}
						int blockdataLength = ChunkConstants.BLOCKS_IN_SECTION >> 3;
						VarNumberSerializer.writeVarInt(buffer, blockdataLength);
						for (int i = 0; i < blockdataLength; i++) {
							for (int j = 1; j <= Long.BYTES; j++) {
								buffer.writeByte(blockstoragePaletted.getRuntimeId((i << 3) + (Long.BYTES - j)));
							}
						}
					} else {
						buffer.writeByte(globalPaletteBitsPerBlock);
						VarNumberSerializer.writeVarInt(buffer, 0);
						NumberBitsStorageCompact writer = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
						for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
							writer.setNumber(blockIndex, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex)));
						}
						ArraySerializer.writeVarIntLongArray(buffer, writer.getStorage());
					}
				} else {
					ChunkWriterUtils.writeBBEmptySection(buffer);
				}

				ChunkWriterUtils.writeBBLight(buffer, chunk.getBlockLight(sectionNumber));
				if (hasSkyLight) {
					ChunkWriterUtils.writeBBLight(buffer, chunk.getSkyLight(sectionNumber));
				}

				sectionPresentConsumer.accept(sectionNumber);
			}
		}
	}

}
