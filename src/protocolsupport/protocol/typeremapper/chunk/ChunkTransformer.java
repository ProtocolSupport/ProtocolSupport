package protocolsupport.protocol.typeremapper.chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntity;

public abstract class ChunkTransformer {

	protected ChunkCoord chunk;
	protected int columnsCount;
	protected boolean hasSkyLight;
	protected boolean hasBiomeData;
	protected final ChunkSection[] sections = new ChunkSection[16];
	protected final int[] biomeData = new int[256];
	protected List<TileEntity> tilesNBTData;
	protected Int2IntMap tilesBlockData;

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable;
	protected final TileDataCache tileCache;
	protected final TileEntityRemapper tileRemapper;
	public ChunkTransformer(ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, TileDataCache tilecache) {
		this.blockDataRemappingTable = blockRemappingTable;
		this.tileRemapper = tileremapper;
		this.tileCache = tilecache;
	}

	public void loadData(ChunkCoord chunk, ByteBuf chunkdata, int bitmap, boolean hasSkyLight, boolean hasBiomeData, TileEntity[] tiles) {
		this.chunk = chunk;
		this.columnsCount = Integer.bitCount(bitmap);
		this.hasSkyLight = hasSkyLight;
		this.hasBiomeData = hasBiomeData;
		for (int i = 0; i < sections.length; i++) {
			if ((bitmap & (1 << i)) != 0) {
				sections[i] = new ChunkSection(chunkdata, hasSkyLight);
			} else {
				sections[i] = null;
			}
		}
		if (hasBiomeData) {
			for (int i = 0; i < biomeData.length; i++) {
				biomeData[i] = chunkdata.readInt();
			}
		}
		this.tilesNBTData = new ArrayList<>(Arrays.asList(tiles));
		this.tilesBlockData = tileCache.getOrCreateChunk(chunk);
	}

	public TileEntity[] remapAndGetTiles() {
		return
			tilesNBTData.stream()
			.map(tile -> tileRemapper.remap(tile, tilesBlockData.get(TileDataCache.getLocalCoordFromPosition(tile.getPosition()))))
			.toArray(TileEntity[]::new);
	}

	protected void processBlockData(int section, int blockindex, int blockdata) {
		int localcoord = TileDataCache.createLocalPositionFromChunkBlock(section, blockindex);
		if (tileRemapper.tileThatNeedsBlockData(blockdata)) {
			tilesBlockData.put(localcoord, blockdata);
		} else {
			tilesBlockData.remove(localcoord);
		}
		if (tileRemapper.usedToBeTile(blockdata)) {
			TileEntity tile = tileRemapper.getLegacyTileFromBlock(getGlobalPosition(section, blockindex), blockdata);
			if (tile != null) {
				tilesNBTData.add(tile);
			}
		}
	}

	protected int getBlockState(int section, BlockStorageReader blockstorage, int blockindex) {
		int blockstate = blockstorage.getBlockData(blockindex);
		if (tileRemapper.tileThatNeedsBlockData(blockstate)) {
			tilesBlockData.put(getLocalPositionFromSectionIndex(section, blockindex), blockstate);
		} else {
			tilesBlockData.remove(getLocalPositionFromSectionIndex(section, blockindex));
		}
		if (tileRemapper.usedToBeTile(blockstate)) {
			TileEntity tile = tileRemapper.getLegacyTileFromBlock(getGlobalPositionFromSectionIndex(section, blockindex), blockstate);
			if (tile != null) {
				tilesNBTData.add(tile);
			}
		}
		return blockstate;
	}

	protected int getLocalPositionFromSectionIndex(int section, int blockindex) {
		return ((blockindex & 0xF) << 12) | (((blockindex >> 4) & 0xF) << 8) | ((section * 16) + ((blockindex >> 8) & 0xF));
	}

	protected Position getGlobalPositionFromSectionIndex(int section, int blockindex) {
		return new Position(
			(chunk.getX() << 4) + (blockindex & 0xF),
			(section * 16) + ((blockindex >> 8) & 0xF),
			(chunk.getZ() << 4) + ((blockindex >> 4) & 0xF)
		);
	}

	protected Position getGlobalPosition(int section, int blockindex) {
		return new Position(
			(chunk.getX() << 4) + (blockindex & 0xF),
			(section * 16) + ((blockindex >> 8) & 0xF),
			(chunk.getZ() << 4) + ((blockindex >> 4) & 0xF)
		);
	}

	protected static final int blocksInSection = 16 * 16 * 16;

	protected static class ChunkSection {

		protected static final int globalPaletteBitsPerBlock = 14;
		protected static final int[] globalPaletteData = new int[MinecraftData.BLOCKDATA_COUNT];
		static {
			for (int i = 0; i < globalPaletteData.length; i++) {
				globalPaletteData[i] = i;
			}
		}

		protected final BlockStorageReader blockdata;
		protected final byte[] blocklight = new byte[2048];
		protected final byte[] skylight = new byte[2048];

		public ChunkSection(ByteBuf datastream, boolean hasSkyLight) {
			byte bitsPerBlock = datastream.readByte();
			int[] palette = globalPaletteData;
			if (bitsPerBlock != globalPaletteBitsPerBlock) {
				palette = ArraySerializer.readVarIntVarIntArray(datastream);
			}
			this.blockdata = new BlockStorageReader(palette, bitsPerBlock, VarNumberSerializer.readVarInt(datastream));
			this.blockdata.readFromStream(datastream);
			datastream.readBytes(blocklight);
			if (hasSkyLight) {
				datastream.readBytes(skylight);
			}
		}

	}

}
