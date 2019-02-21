package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBA extends ChunkTransformer {

	public ChunkTransformerBA(ArrayBasedIdRemappingTable blockDataRemappingTable, TileEntityRemapper tileRemapper, TileDataCache tileCache) {
		super(blockDataRemappingTable, tileRemapper, tileCache);
	}

	public abstract byte[] toLegacyData();

}
