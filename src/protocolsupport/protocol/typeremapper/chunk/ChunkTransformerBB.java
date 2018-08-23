package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public abstract class ChunkTransformerBB extends ChunkTransformer {

	public ChunkTransformerBB(ArrayBasedIdRemappingTable blockRemappingTable) {
		super(blockRemappingTable);
	}

	public abstract void toLegacyData(ByteBuf buffer);

}
