//package protocolsupport.protocol.typeremapper.chunk;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import io.netty.buffer.ByteBuf;
//import protocolsupport.protocol.serializer.ArraySerializer;
//import protocolsupport.protocol.serializer.VarNumberSerializer;
//import protocolsupport.protocol.storage.netcache.ChunkCache;
//import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
//import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
//import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
//import protocolsupport.protocol.utils.types.ChunkCoord;
//import protocolsupport.protocol.utils.types.Position;
//import protocolsupport.protocol.utils.types.TileEntity;
//
//public abstract class ChunkTransformer {
//
//	protected final ArrayBasedIdRemappingTable blockDataRemappingTable;
//	protected final ChunkCache chunkCache;
//	protected final TileEntityRemapper tileRemapper;
//	public ChunkTransformer(ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, ChunkCache chunkCache) {
//		this.blockDataRemappingTable = blockRemappingTable;
//		this.tileRemapper = tileremapper;
//		this.chunkCache = chunkCache;
//	}
//
//	protected ChunkCoord chunk;
//	protected int columnsCount;
//	protected boolean hasSkyLight;
//	protected boolean hasBiomeData;
//	protected final ChunkSection[] sections = new ChunkSection[16];
//	protected final int[] biomeData = new int[256];
//	protected List<TileEntity> tilesNBTData;
//	protected CachedChunk cachedChunk;
//
//	public void loadData(ChunkCoord chunk, ByteBuf chunkdata, int bitmap, boolean hasSkyLight, boolean hasBiomeData, TileEntity[] tiles) {
//		this.chunk = chunk;
//		this.columnsCount = Integer.bitCount(bitmap);
//		this.hasSkyLight = hasSkyLight;
//		this.hasBiomeData = hasBiomeData;
//		for (int i = 0; i < sections.length; i++) {
//			if ((bitmap & (1 << i)) != 0) {
//				sections[i] = new ChunkSection(chunkdata);
//				cachedChunk.setBlocksSection(i, sections[i].blockdata);
//			} else {
//				sections[i] = null;
//				cachedChunk.setBlocksSection(i, null);
//			}
//		}
//		if (hasBiomeData) {
//			for (int i = 0; i < biomeData.length; i++) {
//				biomeData[i] = chunkdata.readInt();
//			}
//		}
//		this.tilesNBTData = new ArrayList<>(Arrays.asList(tiles));
//		if (hasBiomeData) {
//			cachedChunk = chunkCache.add(chunk);
//		} else {
//			cachedChunk = chunkCache.get(chunk);
//		}
//	}
//
//	public TileEntity[] remapAndGetTiles() {
//		return
//			tilesNBTData.stream()
//			.map(tile -> {
//				int sectionNumber = tile.getPosition().getY() >> 4;
//				int x = tile.getPosition().getX() & 0xF;
//				int y = tile.getPosition().getY() & 0xF;
//				int z = tile.getPosition().getZ() & 0xF;
//				return tileRemapper.remap(tile, cachedChunk.getBlock(sectionNumber, CachedChunk.getBlockIndex(x, y, z)));
//			})
//			.toArray(TileEntity[]::new);
//	}
//
//	protected void processBlockData(int sectionNumber, int blockIndex, int blockdata) {
//		if (tileRemapper.usedToBeTile(blockdata)) {
//			TileEntity tile = tileRemapper.getLegacyTileFromBlock(getGlobalPosition(sectionNumber, blockIndex), blockdata);
//			if (tile != null) {
//				tilesNBTData.add(tile);
//			}
//		}
//	}
//
//	protected Position getGlobalPosition(int sectionNumber, int blockIndex) {
//		return new Position(
//			(chunk.getX() << 4) + (blockIndex & 0xF),
//			(sectionNumber * 16) + ((blockIndex >> 8) & 0xF),
//			(chunk.getZ() << 4) + ((blockIndex >> 4) & 0xF)
//		);
//	}
//
//	protected static final int blocksInSection = 16 * 16 * 16;
//
//
//
//}
