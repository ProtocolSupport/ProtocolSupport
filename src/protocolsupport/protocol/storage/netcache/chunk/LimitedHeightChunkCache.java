package protocolsupport.protocol.storage.netcache.chunk;

import java.util.HashMap;
import java.util.Map;

import protocolsupport.protocol.types.ChunkCoord;

public class LimitedHeightChunkCache {

	protected final Map<ChunkCoord, LimitedHeightCachedChunk> chunks = new HashMap<>();

	public LimitedHeightCachedChunk add(ChunkCoord coord) {
		LimitedHeightCachedChunk chunk = new LimitedHeightCachedChunk();
		chunks.put(coord, chunk);
		return chunk;
	}

	public LimitedHeightCachedChunk get(ChunkCoord coord) {
		return chunks.get(coord);
	}

	public void remove(ChunkCoord coord) {
		chunks.remove(coord);
	}

	public void clear() {
		chunks.clear();
	}

}
