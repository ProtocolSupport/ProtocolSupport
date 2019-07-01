package protocolsupport.protocol.storage.netcache.chunk;

import java.util.HashMap;
import java.util.Map;

import protocolsupport.protocol.types.ChunkCoord;

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

}
