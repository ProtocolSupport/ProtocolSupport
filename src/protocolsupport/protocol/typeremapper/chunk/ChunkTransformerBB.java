package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBB extends ChunkTransformer {

	public ChunkTransformerBB(ArrayBasedIdRemappingTable blockRemappingTable, NetworkDataCache cache) {
		super(blockRemappingTable, cache);
	}

	public abstract void writeLegacyData(ByteBuf buffer);

}
