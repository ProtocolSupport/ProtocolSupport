package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBA extends ChunkTransformer {

	public ChunkTransformerBA(ArrayBasedIdRemappingTable blockRemappingTable, TileNBTRemapper tileremapper) {
		super(blockRemappingTable, tileremapper);
	}

	public abstract byte[] toLegacyData();

}
