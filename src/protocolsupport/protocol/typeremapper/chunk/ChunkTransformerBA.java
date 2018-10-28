package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBA extends ChunkTransformer {

	public ChunkTransformerBA(ArrayBasedIdRemappingTable blockRemappingTable, NetworkDataCache cache) {
		super(blockRemappingTable, cache);
	}

	public abstract byte[] toLegacyData();

}
