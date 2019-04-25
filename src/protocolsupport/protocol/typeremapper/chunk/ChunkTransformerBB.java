//package protocolsupport.protocol.typeremapper.chunk;
//
//import io.netty.buffer.ByteBuf;
//import protocolsupport.protocol.storage.netcache.ChunkCache;
//import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
//import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
//
//public abstract class ChunkTransformerBB extends ChunkTransformer {
//
//	public ChunkTransformerBB(ArrayBasedIdRemappingTable blockDataRemappingTable, TileEntityRemapper tileremapper, ChunkCache chunkCache) {
//		super(blockDataRemappingTable, tileremapper, chunkCache);
//	}
//
//	public abstract void writeLegacyData(ByteBuf buffer);
//
//}
