package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.netty.Allocator;

public class ChunkTransformerVaries extends ChunkTransformer {

	protected static final int bitsPerBlock__1_9__1_12 = 13;

	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		ByteBuf chunkdata = Allocator.allocateBuffer();
		try {
			for (int i = 0; i < sections.length; i++) {
				ChunkSection section = sections[i];
				if (section != null) {
					chunkdata.writeByte(bitsPerBlock__1_9__1_12);
					VarNumberSerializer.writeVarInt(chunkdata, 0);
					BlockStorageReader storage = section.blockdata;
					BlockStorageWriter blockstorage = new BlockStorageWriter(bitsPerBlock__1_9__1_12, blocksInSection);
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
