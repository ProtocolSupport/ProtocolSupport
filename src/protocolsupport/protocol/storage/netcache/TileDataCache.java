package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.Position;

public class TileDataCache {

	public static int createLocalPositionFromChunkBlock(int section, int blockindex) {
		return (blockindex << 4) | section;
	}

	public static int createLocalPositionFromMultiChangeBlock(int coord) {
		int x = MiddleBlockChangeMulti.getCoordX(coord);
		int y = MiddleBlockChangeMulti.getCoordY(coord);
		int z = MiddleBlockChangeMulti.getCoordZ(coord);
		return ((y & 0xF) << 12) | (z << 8) | (x << 4) | (y >> 4);
	}

	public static ChunkCoord getChunkCoordsFromPosition(Position position) {
		return new ChunkCoord(position.getX() >> 4, position.getZ() >> 4);
	}

	public static int getLocalCoordFromPosition(Position position) {
		int x = position.getX() & 0xF;
		int y = position.getY() & 0xFF;
		int z = position.getZ() & 0xF;
		return ((y & 0xF) << 12) | (z << 8) | (x << 4) | (y >> 4);
	}


	protected final HashMap<ChunkCoord, Int2IntMap> tileblockdatas = new HashMap<>();

	public Int2IntMap getChunk(ChunkCoord chunkCoord) {
		return tileblockdatas.get(chunkCoord);
	}

	public Int2IntMap getOrCreateChunk(ChunkCoord chunkCoord) {
		return tileblockdatas.computeIfAbsent(chunkCoord, k -> {
			Int2IntMap map = new Int2IntOpenHashMap();
			map.defaultReturnValue(-1);
			return map;
		});
	}

	public void removeChunk(ChunkCoord chunkCoord) {
		tileblockdatas.remove(chunkCoord);
	}

	public int getBlockData(ChunkCoord chunkCoord, int localCoord) {
		Int2IntMap localMap = getChunk(chunkCoord);
		if (localMap != null) {
			return localMap.get(localCoord);
		}
		return -1;
	}

	public void setBlockData(ChunkCoord chunkCoord, int localCoord, int blockdata) {
		getChunk(chunkCoord).put(localCoord, blockdata);
	}

	public void removeBlockData(ChunkCoord chunkCoord, int localCoord) {
		Int2IntMap map = getChunk(chunkCoord);
		if (map != null) {
			map.remove(localCoord);
		}
	}

}