package protocolsupport.protocol.storage.netcache;

import java.util.HashSet;
import java.util.Set;

import protocolsupport.protocol.types.ChunkCoord;

public class PEChunkMapCache {

	protected final Set<ChunkCoord> sentChunks = new HashSet<>();

	public void clear() {
		sentChunks.clear();
	}

	public void markSent(ChunkCoord coord) {
		sentChunks.add(coord);
	}

	public void unmarkSent(ChunkCoord coord) {
		sentChunks.remove(coord);
	}

	public boolean isMarkedAsSent(ChunkCoord coord) {
		return sentChunks.contains(coord);
	}

}
