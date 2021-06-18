package protocolsupport.protocol.typeremapper.chunk;

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
import protocolsupport.utils.BitUtils;

public class ChunkWriterVariesWithLight {

	private ChunkWriterVariesWithLight() {
	}

	public static void writeSectionsCompactFlattening(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable,
		LimitedHeightCachedChunk chunk, int mask, boolean hasSkyLight
	) {
		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (BitUtils.isIBitSet(mask, sectionIndex)) {
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
						int blockdataLength = ChunkConstants.BLOCKS_IN_SECTION >> 3;
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
						NumberBitsStorageCompact writer = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
						for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
							writer.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
						}
						ArrayCodec.writeVarIntLongArray(buffer, writer.getStorage());
					}
				} else {
					ChunkWriteUtils.writeBBEmptySection(buffer);
				}

				ChunkWriteUtils.writeBBLight(buffer, chunk.getBlockLight(sectionIndex));
				if (hasSkyLight) {
					ChunkWriteUtils.writeBBLight(buffer, chunk.getSkyLight(sectionIndex));
				}
			}
		}
	}


	public static void writeSectionsCompactPreFlattening(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockDataRemappingTable,
		LimitedHeightCachedChunk chunk, int mask, boolean hasSkyLight
	) {
		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS; sectionIndex++) {
			if (BitUtils.isIBitSet(mask, sectionIndex)) {
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
						int blockdataLength = ChunkConstants.BLOCKS_IN_SECTION >> 3;
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
						NumberBitsStorageCompact writer = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
						for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
							writer.setNumber(blockIndex, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, section.getBlockData(blockIndex)));
						}
						ArrayCodec.writeVarIntLongArray(buffer, writer.getStorage());
					}
				} else {
					ChunkWriteUtils.writeBBEmptySection(buffer);
				}

				ChunkWriteUtils.writeBBLight(buffer, chunk.getBlockLight(sectionIndex));
				if (hasSkyLight) {
					ChunkWriteUtils.writeBBLight(buffer, chunk.getSkyLight(sectionIndex));
				}
			}
		}
	}

	public static int writeTiles(ByteBuf buffer, LimitedHeightCachedChunk chunk, int mask) {
		int count = 0;
		Map<Position, TileEntity>[] tiles = chunk.getTiles();
		for (int sectionNumber = 0; sectionNumber < tiles.length; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
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
