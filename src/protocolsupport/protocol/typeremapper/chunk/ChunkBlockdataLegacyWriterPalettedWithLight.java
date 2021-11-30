package protocolsupport.protocol.typeremapper.chunk;

import java.util.BitSet;
import java.util.Collection;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.storage.netcache.chunk.BlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.BlockStorageBytePaletted;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightCachedChunk;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;

public class ChunkBlockdataLegacyWriterPalettedWithLight {

	private ChunkBlockdataLegacyWriterPalettedWithLight() {
	}

	public static void writeSectionsBlockdataLightCompactFlattening(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable,
		LimitedHeightCachedChunk chunk, BitSet mask, boolean hasSkyLight
	) {
		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionIndex);

				if (section != null) {
					BlockStorage blockstorage = section.getBlockStorage();
					if (blockstorage instanceof BlockStorageBytePaletted blockstoragePaletted) {
						buffer.writeByte(8);
						BlockStorageBytePaletted.Palette paletteObject = blockstoragePaletted.getPalette();
						short[] palette = paletteObject.getPalette();
						int paletteSize = Math.min(palette.length, paletteObject.getPaletteSize());
						VarNumberCodec.writeVarInt(buffer, paletteSize);
						for (int i = 0; i < paletteSize; i++) {
							VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, palette[i]));
						}
						int blockdataLength = ChunkConstants.SECTION_BLOCK_COUNT >> 3;
						VarNumberCodec.writeVarInt(buffer, blockdataLength);
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
						VarNumberCodec.writeVarInt(buffer, 0);
						NumberBitsStorageCompact writer = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.SECTION_BLOCK_COUNT);
						for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
							writer.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
						}
						ArrayCodec.writeVarIntLongArray(buffer, writer.getStorage());
					}
				} else {
					ChunkLegacyWriteUtils.writeBBEmptySection(buffer);
				}

				ChunkLegacyWriteUtils.writeBBLight(buffer, chunk.getBlockLight(sectionIndex));
				if (hasSkyLight) {
					ChunkLegacyWriteUtils.writeBBLight(buffer, chunk.getSkyLight(sectionIndex));
				}
			}
		}
	}


	public static void writeSectionsBlockdataLightCompactPreFlattening(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockDataRemappingTable,
		LimitedHeightCachedChunk chunk, BitSet mask, boolean hasSkyLight
	) {
		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				CachedChunkSectionBlockStorage section = chunk.getBlocksSection(sectionIndex);

				if (section != null) {
					BlockStorage blockstorage = section.getBlockStorage();
					if (blockstorage instanceof BlockStorageBytePaletted blockstoragePaletted) {
						buffer.writeByte(Byte.SIZE);
						BlockStorageBytePaletted.Palette paletteObject = blockstoragePaletted.getPalette();
						short[] palette = paletteObject.getPalette();
						int paletteSize = Math.min(palette.length, paletteObject.getPaletteSize());
						VarNumberCodec.writeVarInt(buffer, paletteSize);
						for (int i = 0; i < paletteSize; i++) {
							VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, palette[i]));
						}
						int blockdataLength = ChunkConstants.SECTION_BLOCK_COUNT >> 3;
						VarNumberCodec.writeVarInt(buffer, blockdataLength);
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
						VarNumberCodec.writeVarInt(buffer, 0);
						NumberBitsStorageCompact writer = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.SECTION_BLOCK_COUNT);
						for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
							writer.setNumber(blockIndex, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex)));
						}
						ArrayCodec.writeVarIntLongArray(buffer, writer.getStorage());
					}
				} else {
					ChunkLegacyWriteUtils.writeBBEmptySection(buffer);
				}

				ChunkLegacyWriteUtils.writeBBLight(buffer, chunk.getBlockLight(sectionIndex));
				if (hasSkyLight) {
					ChunkLegacyWriteUtils.writeBBLight(buffer, chunk.getSkyLight(sectionIndex));
				}
			}
		}
	}

	public static int writeTiles(ByteBuf buffer, LimitedHeightCachedChunk chunk, BitSet mask) {
		int count = 0;
		Map<Position, TileEntity>[] tiles = chunk.getTiles();
		for (int sectionNumber = 0; sectionNumber < tiles.length; sectionNumber++) {
			if (mask.get(sectionNumber)) {
				Collection<TileEntity> sectionTiles = tiles[sectionNumber].values();
				for (TileEntity tile : sectionTiles) {
					ItemStackCodec.writeDirectTag(buffer, tile.getNBT());
				}
				count += sectionTiles.size();
			}
		}
		return count;
	}

}
