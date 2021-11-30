package protocolsupport.protocol.typeremapper.chunk;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectionData;
import protocolsupport.protocol.types.chunk.IPalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorageGlobal;
import protocolsupport.protocol.types.chunk.PalettedStorageSingle;
import protocolsupport.protocol.utils.NumberBitsStorageCompact;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public class ChunkBlockdataLegacyWriterPaletted {

	private ChunkBlockdataLegacyWriterPaletted() {
	}

	public static void writeSectionsBlockdataPadded(
		ByteBuf buffer,
		int globalBitsPerBlock,
		IntMappingTable blockLegacyDataTable, FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectionData[] sections, BitSet sectionMask
	) {
		for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
			if (sectionMask.get(sectionIndex)) {
				ChunkSectionData section = sections[sectionIndex];
				IPalettedStorage storage = section.getBlockData();

				buffer.writeShort(section.getNonAirBlockCount());
				if (storage instanceof PalettedStorage palettedStorage) {
					buffer.writeByte(storage.getBitsPerNumber());
					short[] palette = palettedStorage.getPalette();
					VarNumberCodec.writeVarInt(buffer, palette.length);
					for (short blockdata : palette) {
						VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, blockdata));
					}
					ArrayCodec.writeVarIntLongArray(buffer, palettedStorage.getStorage());
				} else if (storage instanceof PalettedStorageGlobal) {
					buffer.writeByte(globalBitsPerBlock);
					NumberBitsStoragePadded blockstorage = new NumberBitsStoragePadded(globalBitsPerBlock, ChunkConstants.SECTION_BLOCK_COUNT);
					for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
						blockstorage.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, storage.getId(blockIndex)));
					}
					ArrayCodec.writeVarIntLongArray(buffer, blockstorage.getStorage());
				} else if (storage instanceof PalettedStorageSingle palettedStorageSingle) {
					buffer.writeByte(4);
					VarNumberCodec.writeVarInt(buffer, 1);
					VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, palettedStorageSingle.getId()));
					ArrayCodec.writeVarIntLongArray(buffer, new NumberBitsStoragePadded(4, ChunkConstants.SECTION_BLOCK_COUNT).getStorage());
				}
			}
		}
	}

	public static void writeSectionsBlockdataCompact(
		ByteBuf buffer,
		int globalPaletteBitsPerBlock,
		IntMappingTable blockLegacyDataTable, FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectionData[] sections, BitSet sectionMask
	) {
		for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
			if (sectionMask.get(sectionIndex)) {
				ChunkSectionData section = sections[sectionIndex];
				IPalettedStorage storage = section.getBlockData();

				buffer.writeShort(section.getNonAirBlockCount());
				if (storage instanceof PalettedStorage palettedStorage) {
					buffer.writeByte(8);
					short[] palette = palettedStorage.getPalette();
					VarNumberCodec.writeVarInt(buffer, palette.length);
					for (short blockdata : palette) {
						VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, blockdata));
					}
					int blockdataLength = ChunkConstants.SECTION_BLOCK_COUNT >> 3;
					VarNumberCodec.writeVarInt(buffer, blockdataLength);
					for (int paletteLongIndex = 0; paletteLongIndex < blockdataLength; paletteLongIndex++) {
						int blockIndex = paletteLongIndex << 3;
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 7));
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 6));
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 5));
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 4));
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 3));
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 2));
						buffer.writeByte(palettedStorage.getNumber(blockIndex | 1));
						buffer.writeByte(palettedStorage.getNumber(blockIndex));
					}
				} else if (storage instanceof PalettedStorageGlobal) {
					buffer.writeByte(globalPaletteBitsPerBlock);
					NumberBitsStorageCompact blockstorage = new NumberBitsStorageCompact(globalPaletteBitsPerBlock, ChunkConstants.SECTION_BLOCK_COUNT);
					for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
						blockstorage.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, storage.getId(blockIndex)));
					}
					ArrayCodec.writeVarIntLongArray(buffer, blockstorage.getStorage());
				} else if (storage instanceof PalettedStorageSingle palettedStorageSingle) {
					buffer.writeByte(4);
					VarNumberCodec.writeVarInt(buffer, 1);
					VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, palettedStorageSingle.getId()));
					ArrayCodec.writeVarIntLongArray(buffer, new NumberBitsStorageCompact(4, ChunkConstants.SECTION_BLOCK_COUNT).getStorage());
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
