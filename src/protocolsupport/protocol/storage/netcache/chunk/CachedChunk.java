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
	}

	public void setSkyLightSection(int sectionNumber, byte[] skyLight) {
		this.skyLight[sectionNumber] = skyLight;
	}

	public void setBlockLightSection(int sectionNumber, byte[] blockLight) {
		this.blockLight[sectionNumber] = blockLight;
	}

	public void setBlock(int sectionNumber, int blockIndex, short blockdata) {
		CachedChunkSectionBlockStorage section = blocks[sectionNumber];
		if (section == null) {
			section = new CachedChunkSectionBlockStorage();
			blocks[sectionNumber] = section;
		}
		section.setBlockData(blockIndex, blockdata);
	}
}