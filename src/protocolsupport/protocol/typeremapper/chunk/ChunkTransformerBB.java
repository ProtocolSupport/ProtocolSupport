package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBB extends ChunkTransformer {

	public ChunkTransformerBB(ArrayBasedIdRemappingTable blockRemappingTable, TileNBTRemapper tileremapper, TileDataCache tilecache) {
		super(blockRemappingTable, tileremapper, tilecache);
	}

	public abstract void writeLegacyData(ByteBuf buffer);

}
