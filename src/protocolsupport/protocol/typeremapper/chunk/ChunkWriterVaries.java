package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.utils.Utils;

public class ChunkWriterVaries {

	public static void writeSections(
		ByteBuf buffer, int mask, int globalPaletteBitsPerBlock,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		FlatteningBlockDataTable flatteningBlockDataTable,
		ChunkSectonBlockData[] sections
	) {
		for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
			if (Utils.isBitSet(mask, sectionNumber)) {
				ChunkSectonBlockData section = sections[sectionNumber];

				buffer.writeShort(section.getNonAirBlockCount());
				buffer.writeByte(globalPaletteBitsPerBlock);
				BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock);
				for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
					blockstorage.setBlockState(blockIndex, BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, section.getBlockData(blockIndex)));
				}
				ArraySerializer.writeVarIntLongArray(buffer, blockstorage.getBlockData());
			}
		}
	}

}
