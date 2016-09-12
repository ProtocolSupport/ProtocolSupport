package protocolsupport.protocol.legacyremapper.chunk;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_10_R1.DataBits;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;

public class ChunkTransformerVaries extends ChunkTransformer {

	protected static final int bitsPerBlock19 = 13;

	@Override
	protected byte[] toLegacyData0(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = IdRemapper.BLOCK.getTable(version);
		ByteBuf chunkdata = Allocator.allocateBuffer();
		try {
			for (int i = 0; i < columnsCount; i++) {
				ChunkSection section = sections[i];
				chunkdata.writeByte(bitsPerBlock19);
				ChannelUtils.writeVarInt(chunkdata, 0);
				BlockStorage storage = section.blockdata;
				DataBits databits = new DataBits(bitsPerBlock19, blocksInSection);
				for (int block = 0; block < blocksInSection; block++) {
					databits.a(block, table.getRemap(storage.getBlockState(block)));
				}
				long[] ldata = databits.a();
				ChannelUtils.writeVarInt(chunkdata, ldata.length);
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
			return ChannelUtils.toArray(chunkdata);
		} finally {
			chunkdata.release();
		}
	}

}
