package protocolsupport.protocol.legacyremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.netty.Allocator;

public class ChunkTransformerVaries extends ChunkTransformer {

	protected static final int bitsPerBlock__1_9__1_11 = 13;

	@Override
	protected byte[] toLegacyData0(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		ByteBuf chunkdata = Allocator.allocateBuffer();
		try {
			for (int i = 0; i < columnsCount; i++) {
				ChunkSection section = sections[i];
				chunkdata.writeByte(bitsPerBlock__1_9__1_11);
				VarNumberSerializer.writeVarInt(chunkdata, 0);
				BlockStorageReader storage = section.blockdata;
				BlockStorageWriter blockstorage = new BlockStorageWriter(bitsPerBlock__1_9__1_11, blocksInSection);
				for (int block = 0; block < blocksInSection; block++) {
					blockstorage.setBlockState(block, table.getRemap(storage.getBlockState(block)));
				}
				long[] ldata = blockstorage.getBlockData();
				VarNumberSerializer.writeVarInt(chunkdata, ldata.length);
				for (long l : ldata) {
					chunkdata.writeLong(l);
				}
				chunkdata.writeBytes(section.blocklight);
				if (hasSkyLight) {
					chunkdata.writeBytes(section.skylight);
				}
			}
			if (hasBiomeData) {
				chunkdata.writeBytes(biomeData);
			}
			return MiscSerializer.readAllBytes(chunkdata);
		} finally {
			chunkdata.release();
		}
	}

}
