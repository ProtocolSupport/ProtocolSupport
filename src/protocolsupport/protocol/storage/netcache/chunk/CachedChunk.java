package protocolsupport.protocol.storage.netcache.chunk;

import java.util.HashMap;
import java.util.Map;

import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public class CachedChunk {

	public static int getBlockIndex(int x, int y, int z) {
		return (y << 8) | (z << 4) | (x);
	}

	protected boolean full = false;
	protected final CachedChunkSectionBlockStorage[] blocks = new CachedChunkSectionBlockStorage[ChunkConstants.SECTION_COUNT_BLOCKS];
	protected final byte[][] skyLight = new byte[ChunkConstants.SECTION_COUNT_BLOCKS][];
	protected final byte[][] blockLight = new byte[ChunkConstants.SECTION_COUNT_BLOCKS][];

	//stored should be remapped
	@SuppressWarnings("unchecked")
	protected final Map<Position, TileEntity>[] tiles = new HashMap[ChunkConstants.SECTION_COUNT_BLOCKS];
	{
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new HashMap<>();
		}
	}

	public boolean isFull() {
		return full;
	}

	public boolean checkHadFull() {
		if (!full) {
			full = true;
			return false;
		}
		return true;
	}

	public CachedChunkSectionBlockStorage getBlocksSection(int sectionNumber) {
		return blocks[sectionNumber];
	}

	public byte[] getSkyLight(int sectionNumber) {
		return skyLight[sectionNumber];
	}

	public byte[] getBlockLight(int sectionNumber) {
		return blockLight[sectionNumber];
	}

	public Map<Position, TileEntity>[] getTiles() {
		return tiles;
	}

	public Map<Position, TileEntity> getTiles(int sectionNumber) {
		return tiles[sectionNumber];
	}

	public int getBlock(int sectionNumber, int blockIndex) {
		CachedChunkSectionBlockStorage section = blocks[sectionNumber];
		if (section == null) {
			return 0;
		}
		return section.getBlockData(blockIndex);
	}

	public void setBlocksSection(int sectionNumber, CachedChunkSectionBlockStorage section) {
		this.blocks[sectionNumber] = section;
		this.tiles[sectionNumber].clear();
	}

	public void setSkyLightSection(int sectionNumber, byte[] skyLight) {
		this.skyLight[sectionNumber] = skyLight;
	}

	public void setBlockLightSection(int sectionNumber, byte[] blockLight) {
		this.blockLight[sectionNumber] = blockLight;
	}

	public void setBlock(int sectionNumber, int blockIndex, short blockdata) {
		//TODO: remove tile on block type change
		CachedChunkSectionBlockStorage section = this.blocks[sectionNumber];
		if (section == null) {
			section = new CachedChunkSectionBlockStorage();
			this.blocks[sectionNumber] = section;
		}
		section.setBlockData(blockIndex, blockdata);
	}
}