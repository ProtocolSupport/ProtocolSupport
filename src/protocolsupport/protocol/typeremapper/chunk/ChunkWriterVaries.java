package protocolsupport.protocol.typeremapper.chunk;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public class ChunkWriterVaries {

	private ChunkWriterVaries() {
	}

	public static void writeSectionsPadded(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockLegacyDataTable, FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectonBlockData[] sections, BitSet sectionMask
	) {
		for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
			if (sectionMask.get(sectionIndex)) {
				ChunkSectonBlockData section = sections[sectionIndex];

				int bitsPerBlock = section.getBitsPerNumber();
				if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(bitsPerBlock);
					short[] palette = section.getPalette();
					VarNumberCodec.writeVarInt(buffer, palette.length);
					for (short blockdata : palette) {
						VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, blockdata));
					}
					ArrayCodec.writeVarIntLongArray(buffer, section.getStorage());
				} else {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(globalPaletteBitsPerBlock);
					NumberBitsStoragePadded blockstorage = new NumberBitsStoragePadded(globalPaletteBitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						blockstorage.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
					}
					ArrayCodec.writeVarIntLongArray(buffer, blockstorage.getStorage());
				}
			}
		}
	}

	public static void writeSectionsCompact(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectonBlockData[] sections, BitSet sectionMask
	) {
		for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
			if (sectionMask.get(sectionIndex)) {
				ChunkSectonBlockData section = sections[sectionIndex];

				int bitsPerBlock = section.getBitsPerNumber();
				if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
					buffer.writeShort(section.getNonAirBlockCount());
					buffer.writeByte(8);
					short[] palette = section.getPalette();
					VarNumberCodec.writeVarInt(buffer, palette.length);
					for (short blockdata : palette) {
						VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockdata));
					}
					int blockdataLength = ChunkConstants.BLOCKS_IN_SECTION >> 3;
					VarNumberCodec.writeVarInt(buffer, blockdataLength);
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
					ArrayCodec.writeVarIntLongArray(buffer, blockstorage.getStorage());
				}
			}
		}
	}

	public static void writeLight(ClientBoundPacketData packet, byte[][] lightArrays, BitSet mask) {
		for (int sectionIndex = 0; sectionIndex < lightArrays.length; sectionIndex++) {
			if (mask.get(sectionIndex)) {
				ArrayCodec.writeVarIntByteArray(packet, lightArrays[sectionIndex]);
			}
		}
	}

}
