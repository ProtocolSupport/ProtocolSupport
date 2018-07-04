package protocolsupport.protocol.storage.netcache;

import java.util.HashSet;
import java.util.Set;

public class PEChunkMapCache {

	protected final Set<ChunkCoord> sentChunks = new HashSet<>();

	public void clear() {
		sentChunks.clear();
	}

	public void markSent(int x, int z) {
		sentChunks.add(new ChunkCoord(x, z));
	}

	public void unmarkSent(int x, int z) {
		sentChunks.remove(new ChunkCoord(x, z));
	}

	public boolean isMarkedAsSent(int x, int z) {
		return sentChunks.contains(new ChunkCoord(x, z));
	}

	protected static class ChunkCoord {
		private final int x;
		private final int z;
		public ChunkCoord(int x, int z) {
			this.x = x;
			this.z = z;
		}
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ChunkCoord)) {
				return false;
			}
			ChunkCoord other = (ChunkCoord) obj;
			return (x == other.x) && (z == other.z);
		}
		@Override
		public int hashCode() {
			return (x * 31) + z;
		}
	}

}
