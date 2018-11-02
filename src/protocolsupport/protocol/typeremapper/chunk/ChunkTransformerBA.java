package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBA extends ChunkTransformer {

	public ChunkTransformerBA(ArrayBasedIdRemappingTable blockRemappingTable, TileDataCache tilecache) {
		super(blockRemappingTable, tilecache);
	}

	public abstract byte[] toLegacyData();

}
