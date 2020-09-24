package protocolsupport.protocol.typeremapper.chunk;

import java.util.Collection;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.chunk.BlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.BlockStorageBytePaletted;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;
import protocolsupport.utils.BitUtils;

public class ChunkWriterVariesWithLight {

	public static void writeSectionsCompactFlattening(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		IdMappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		CachedChunk chunk, boolean hasSkyLight
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
						for (int paletteLongIndex = 0; paletteLongIndex < blockdataLength; paletteLongIndex++) {
							int blockIndex = paletteLongIndex << 3;
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 7));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 6));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 5));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 4));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 3));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 2));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 1));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex));
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
			}
		}
	}


	public static void writeSectionsCompactPreFlattening(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		IdMappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight
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
						for (int paletteLongIndex = 0; paletteLongIndex < blockdataLength; paletteLongIndex++) {
							int blockIndex = paletteLongIndex << 3;
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 7));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 6));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 5));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 4));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 3));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 2));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex | 1));
							buffer.writeByte(blockstoragePaletted.getRuntimeId(blockIndex));
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
			}
		}
	}

	public static int writeTiles(ByteBuf buffer, int mask, CachedChunk chunk) {
		int count = 0;
		Map<Position, TileEntity>[] tiles = chunk.getTiles();
		for (int sectionNumber = 0; sectionNumber < tiles.length; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				Collection<TileEntity> sectionTiles = tiles[sectionNumber].values();
				for (TileEntity tile : sectionTiles) {
					ItemStackSerializer.writeDirectTag(buffer, tile.getNBT());
				}
				count += sectionTiles.size();
			}
		}
		return count;
	}

}
