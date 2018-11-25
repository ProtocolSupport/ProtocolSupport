package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.Position;

public class TileDataCache {

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

	public int getBlockData(Position position) {
		return getBlockData(position.getChunkCoord(), position.getLocalCoord());
	}

	public int getBlockData(ChunkCoord chunkCoord, int localCoord) {
		Int2IntMap localMap = getChunk(chunkCoord);
		if (localMap != null) {
			return localMap.get(localCoord);
		}
		return -1;
	}

	public void setBlockData(Position position, int blockdata) {
		setBlockData(position.getChunkCoord(), position.getLocalCoord(), blockdata);
	}

	public void setBlockData(ChunkCoord chunkCoord, int localCoord, int blockdata) {
		getOrCreateChunk(chunkCoord).put(localCoord, blockdata);
	}

	public void removeBlockData(Position position) {
		removeBlockData(position.getChunkCoord(), position.getLocalCoord());
	}

	public void removeBlockData(ChunkCoord chunkCoord, int localCoord) {
		Int2IntMap map = getChunk(chunkCoord);
		if (map != null) {
			map.remove(localCoord);
		}
	}

}