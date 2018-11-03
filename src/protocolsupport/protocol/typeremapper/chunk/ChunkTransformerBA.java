package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBA extends ChunkTransformer {

	public ChunkTransformerBA(ArrayBasedIdRemappingTable blockRemappingTable, TileNBTRemapper tileremapper, TileDataCache tilecache) {
		super(blockRemappingTable, tileremapper, tilecache);
	}

	public abstract byte[] toLegacyData();

}
