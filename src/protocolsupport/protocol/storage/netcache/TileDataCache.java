package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.LocalCoord;
import protocolsupport.protocol.utils.types.Position;

public class TileDataCache {

	protected final TileNBTRemapper tileremapper;

	public TileDataCache(ConnectionImpl connection) {
		this.tileremapper =  new TileNBTRemapper(connection);
	}

	public TileNBTRemapper getTileRemapper() {
		return tileremapper;
	}

	protected final Map<ChunkCoord, Object2IntOpenHashMap<LocalCoord>> tiledata = new HashMap<>();

	public int getCachedTileBlockstate(Position position) {
		return getCachedTileBlockstate(ChunkCoord.fromGlobal(position), LocalCoord.fromGlobal(position));
	}

	public void setCachedTileBlockstate(Position position, int blockstate) {
		setCachedTileBlockstate(ChunkCoord.fromGlobal(position), LocalCoord.fromGlobal(position), blockstate);
	}
	public int getCachedTileBlockstate(ChunkCoord chunkCoord, LocalCoord localCoord) {
		Object2IntOpenHashMap<LocalCoord> map = tiledata.get(chunkCoord);
		if (map != null) {
			return map.getInt(localCoord);
		}
		return -1;
	}

	public void setCachedTileBlockstate(ChunkCoord chunkCoord, LocalCoord localCoord, int blockstate) {
		if (blockstate == 0) {
			tiledata.computeIfPresent(chunkCoord, (key, map) -> {
				map.removeInt(localCoord);
				return map;
			});
		} else {
			tiledata.computeIfAbsent(chunkCoord, k -> new Object2IntOpenHashMap<>()).put(localCoord, blockstate);
		}
	}

	public void clearCache(ChunkCoord chunk) {
		tiledata.remove(chunk);
	}

}
