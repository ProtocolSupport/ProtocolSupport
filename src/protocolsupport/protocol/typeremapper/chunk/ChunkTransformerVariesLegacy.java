package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyBlockId;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.netty.Allocator;

public class ChunkTransformerVariesLegacy extends ChunkTransformer {

	protected static final int bitsPerBlock = 13;

	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		ByteBuf chunkdata = Allocator.allocateBuffer();
		try {
			for (int i = 0; i < sections.length; i++) {
				ChunkSection section = sections[i];
				if (section != null) {
					chunkdata.writeByte(bitsPerBlock);
					VarNumberSerializer.writeVarInt(chunkdata, 0);
					BlockStorageReader storage = section.blockdata;
					BlockStorageWriter blockstorage = new BlockStorageWriter(bitsPerBlock, blocksInSection);
					for (int block = 0; block < blocksInSection; block++) {
						blockstorage.setBlockState(block, LegacyBlockId.getLegacyCombinedId(table.getRemap(storage.getBlockState(block))));
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
