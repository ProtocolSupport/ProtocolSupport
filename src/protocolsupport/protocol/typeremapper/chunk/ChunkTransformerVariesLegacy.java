package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.netty.Allocator;

public class ChunkTransformerVariesLegacy extends ChunkTransformer {

	protected static final int globalPaletteBitsPerBlock = 13;

	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = LegacyBlockData.REGISTRY.getTable(version);
		ByteBuf chunkdata = Allocator.allocateBuffer();
		try {
			for (int i = 0; i < sections.length; i++) {
				ChunkSection section = sections[i];
				if (section != null) {
					BlockStorageReader storage = section.blockdata;
					int bitsPerBlock = storage.getBitsPerBlock();
					if (bitsPerBlock > 8) {
						chunkdata.writeByte(globalPaletteBitsPerBlock);
						VarNumberSerializer.writeVarInt(chunkdata, 0);
						BlockStorageWriter blockstorage = new BlockStorageWriter(globalPaletteBitsPerBlock, blocksInSection);
						for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
							blockstorage.setBlockState(blockIndex, PreFlatteningBlockIdData.getCombinedId(table.getRemap(storage.getBlockState(blockIndex))));
						}
						ArraySerializer.writeVarIntLongArray(chunkdata, blockstorage.getBlockData());
					} else {
						chunkdata.writeByte(bitsPerBlock);
						BlockPalette palette = new BlockPalette();
						BlockStorageWriter blockstorage = new BlockStorageWriter(bitsPerBlock, blocksInSection);
						for (int blockIndex = 0; blockIndex < blocksInSection; blockIndex++) {
							blockstorage.setBlockState(blockIndex, palette.getRuntimeId(PreFlatteningBlockIdData.getCombinedId(table.getRemap(storage.getBlockState(blockIndex)))));
						}
						ArraySerializer.writeVarIntVarIntArray(chunkdata, palette.getBlockStates());
						ArraySerializer.writeVarIntLongArray(chunkdata, blockstorage.getBlockData());
					}
					chunkdata.writeBytes(section.blocklight);
					if (hasSkyLight) {
						chunkdata.writeBytes(section.skylight);
					}
				}
			}
			if (hasBiomeData) {
				for (int i = 0; i < biomeData.length; i++) {
					chunkdata.writeByte(biomeData[i]);
				}
			}
			return MiscSerializer.readAllBytes(chunkdata);
		} finally {
			chunkdata.release();
		}
	}

}
