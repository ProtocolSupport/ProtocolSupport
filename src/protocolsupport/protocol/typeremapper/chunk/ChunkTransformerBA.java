package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBA extends ChunkTransformer {

	public ChunkTransformerBA(ArrayBasedIdRemappingTable blockRemappingTable) {
		super(blockRemappingTable);
	}

	public abstract byte[] toLegacyData();

}
