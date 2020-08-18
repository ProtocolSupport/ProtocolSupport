package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;
import protocolsupport.utils.BitUtils;

public class ChunkWriterVaries {

	public static void writeSectionsPadded(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		IdMappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectonBlockData[] sections
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				ChunkSectonBlockData section = sections[sectionNumber];

				int bitsPerBlock = section.getBitsPerNumber();
				if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(bitsPerBlock);
					short[] palette = section.getPalette();
					VarNumberSerializer.writeVarInt(buffer, palette.length);
					for (short blockdata : palette) {
						VarNumberSerializer.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockdata));
					}
					ArraySerializer.writeVarIntLongArray(buffer, section.getStorage());
				} else {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(globalPaletteBitsPerBlock);
					NumberBitsStoragePadded blockstorage = new NumberBitsStoragePadded(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						blockstorage.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
					}
					ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getStorage());
				}
			}
		}
	}

	public static void writeSectionsCompact(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		IdMappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectonBlockData[] sections
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (BitUtils.isIBitSet(mask, sectionNumber)) {
				ChunkSectonBlockData section = sections[sectionNumber];

				int bitsPerBlock = section.getBitsPerNumber();
				if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(8);
					short[] palette = section.getPalette();
					VarNumberSerializer.writeVarInt(buffer, palette.length);
					for (short blockdata : palette) {
						VarNumberSerializer.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockdata));
					}
					int blockdataLength = ChunkConstants.BLOCKS_IN_SECTION >> 3;
					VarNumberSerializer.writeVarInt(buffer, blockdataLength);
					for (int paletteLongIndex = 0; paletteLongIndex < blockdataLength; paletteLongIndex++) {
						int blockIndex = paletteLongIndex << 3;
						buffer.writeByte(section.getNumber(blockIndex | 7));
						buffer.writeByte(section.getNumber(blockIndex | 6));
						buffer.writeByte(section.getNumber(blockIndex | 5));
						buffer.writeByte(section.getNumber(blockIndex | 4));
						buffer.writeByte(section.getNumber(blockIndex | 3));
						buffer.writeByte(section.getNumber(blockIndex | 2));
						buffer.writeByte(section.getNumber(blockIndex | 1));
						buffer.writeByte(section.getNumber(blockIndex));
					}
				} else {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(globalPaletteBitsPerBlock);
					NumberBitsStorageCompact blockstorage = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						blockstorage.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
					}
					ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getStorage());
				}
			}
		}
	}

}
