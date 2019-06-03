package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;
import java.util.Map;

import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.BlocksSection;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public class ChunkCache {

	protected final Map<ChunkCoord, CachedChunk> chunks = new HashMap<>();

	public CachedChunk add(ChunkCoord coord) {
		CachedChunk chunk = new CachedChunk();
		chunks.put(coord, chunk);
		return chunk;
	}

	public CachedChunk get(ChunkCoord coord) {
		return chunks.get(coord);
	}

	public void remove(ChunkCoord coord) {
		chunks.remove(coord);
	}

	public static class CachedChunk {

		public static int getBlockIndex(int x, int y, int z) {
			return (y << 8) | (z << 4) | (x);
		}

		protected final BlocksSection[] blocks = new BlocksSection[ChunkConstants.SECTION_COUNT_BLOCKS];
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

		public BlocksSection getBlocksSection(int sectionNumber) {
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
			BlocksSection section = blocks[sectionNumber];
			if (section == null) {
				return 0;
			}
			return section.getBlockData(blockIndex);
		}

		public void setBlocksSection(int sectionNumber, BlocksSection section) {
			this.blocks[sectionNumber] = section;
		}

		public void setSkyLightSection(int sectionNumber, byte[] skyLight) {
			this.skyLight[sectionNumber] = skyLight;
		}

		public void setBlockLightSection(int sectionNumber, byte[] blockLight) {
			this.blockLight[sectionNumber] = blockLight;
		}

		public void setBlock(int sectionNumber, int blockIndex, int blockdata) {
			BlocksSection section = blocks[sectionNumber];
			if (section == null) {
				section = new BlocksSection();
				if (blockdata != 0) {
					section.setBlockCount(1);
				}
				blocks[sectionNumber] = section;
			}
			section.setBlockData(blockIndex, blockdata);
		}
	}

}
