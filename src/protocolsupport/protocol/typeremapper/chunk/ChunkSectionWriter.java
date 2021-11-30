package protocolsupport.protocol.typeremapper.chunk;

import org.bukkit.NamespacedKey;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.storage.netcache.IBiomeRegistry;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectionData;
import protocolsupport.protocol.types.chunk.IPalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorageGlobal;
import protocolsupport.protocol.types.chunk.PalettedStorageSingle;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;
import protocolsupport.utils.MiscUtils;

public class ChunkSectionWriter {

	public static void writeSectionsBlockdataBiomes(
		ByteBuf buffer,
		int bitsPerBlockGlobal,
		IntMappingTable blockLegacyDataTable, FlatteningBlockDataTable flatteningBlockDataTable,
		IBiomeRegistry biomeRegistry, GenericMappingTable<NamespacedKey> biomeLegacyDataTable,
		ChunkSectionData[] sections
	) {
		for (ChunkSectionData section : sections) {
			buffer.writeShort(section.getNonAirBlockCount());

			IPalettedStorage blockdataStorage = section.getBlockData();
			if (blockdataStorage instanceof PalettedStorage blockdataStoragePaletted) {
				buffer.writeByte(blockdataStoragePaletted.getBitsPerNumber());
				short[] palette = blockdataStoragePaletted.getPalette();
				VarNumberCodec.writeVarInt(buffer, palette.length);
				for (short blockdata : palette) {
					VarNumberCodec.writeVarInt(buffer, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, blockdata));
				}
				ArrayCodec.writeVarIntLongArray(buffer, blockdataStoragePaletted.getStorage());
			} else if (blockdataStorage instanceof PalettedStorageSingle blockdataStoragePalettedSingle) {
				buffer.writeByte(0);
				VarNumberCodec.writeVarInt(buffer, blockdataStoragePalettedSingle.getId());
				VarNumberCodec.writeVarInt(buffer, 0); //storage length (always empty)
			} else if (blockdataStorage instanceof PalettedStorageGlobal) {
				buffer.writeByte(bitsPerBlockGlobal);
				NumberBitsStoragePadded storage = new NumberBitsStoragePadded(bitsPerBlockGlobal, ChunkConstants.SECTION_BLOCK_COUNT);
				for (int blockIndex = 0; blockIndex < ChunkConstants.SECTION_BLOCK_COUNT; blockIndex++) {
					storage.setNumber(blockIndex, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, blockdataStorage.getId(blockIndex)));
				}
				ArrayCodec.writeVarIntLongArray(buffer, storage.getStorage());
			}

			IPalettedStorage biomeStorage = section.getBiomes();
			if (biomeStorage instanceof PalettedStorage biomeStoragePaletted) {
				buffer.writeByte(biomeStoragePaletted.getBitsPerNumber());
				short[] palette = biomeStoragePaletted.getPalette();
				VarNumberCodec.writeVarInt(buffer, palette.length);
				for (short biome : palette) {
					VarNumberCodec.writeVarInt(buffer, BiomeTransformer.mapCustomBiome(biomeRegistry, biomeLegacyDataTable, biome));
				}
				ArrayCodec.writeVarIntLongArray(buffer, biomeStoragePaletted.getStorage());
			} else if (biomeStorage instanceof PalettedStorageSingle biomeStoragePalettedSingle) {
				buffer.writeByte(0);
				VarNumberCodec.writeVarInt(buffer, biomeStoragePalettedSingle.getId());
				VarNumberCodec.writeVarInt(buffer, 0); //storage length (always empty)
			} else if (biomeStorage instanceof PalettedStorageGlobal) {
				buffer.writeByte(MiscUtils.ceilLog2(biomeRegistry.getBiomesCount()));
				NumberBitsStoragePadded storage = new NumberBitsStoragePadded(bitsPerBlockGlobal, ChunkConstants.SECTION_BIOME_COUNT);
				for (int biomeIndex = 0; biomeIndex < ChunkConstants.SECTION_BIOME_COUNT; biomeIndex++) {
					storage.setNumber(biomeIndex, BiomeTransformer.mapCustomBiome(biomeRegistry, biomeLegacyDataTable, biomeStorage.getId(biomeIndex)));
				}
				ArrayCodec.writeVarIntLongArray(buffer, storage.getStorage());
			}
		}
	}

}
