package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.LocalCoord;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public class TileDataCache {

	protected final TileNBTRemapper tileremapper;

	public TileDataCache(ConnectionImpl connection) {
		this.tileremapper =  new TileNBTRemapper(connection);
	}

	public TileNBTRemapper getTileRemapper() {
		return tileremapper;
	}

	protected final Map<ChunkCoord, Object2IntOpenHashMap<LocalCoord>> tiledata = new HashMap<>();
	protected final Map<ChunkCoord, HashMap<LocalCoord, NBTCompound>> halfnbt = new HashMap<>();

	public int getCachedTileBlockstate(Position position) {
		return getCachedTileBlockstate(ChunkCoord.fromGlobal(position), LocalCoord.fromGlobal(position));
	}

	public void setCachedTileBlockstate(Position position, int blockstate) {
		setCachedTileBlockstate(ChunkCoord.fromGlobal(position), LocalCoord.fromGlobal(position), blockstate);
	}

	public NBTCompound getCachedTileNBT(Position position) {
		return getCachedTileNBT(ChunkCoord.fromGlobal(position), LocalCoord.fromGlobal(position));
	}

	public void setCachedTileNBT(Position position, NBTCompound tile) {
		setCachedTileNBT(ChunkCoord.fromGlobal(position), LocalCoord.fromGlobal(position), tile);
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
			halfnbt.computeIfPresent(chunkCoord, (key, map) -> {
				map.remove(localCoord);
				return map;
			});
		} else {
			tiledata.computeIfAbsent(chunkCoord, k -> new Object2IntOpenHashMap<>()).put(localCoord, blockstate);
		}
	}

	public NBTCompound getCachedTileNBT(ChunkCoord chunkCoord, LocalCoord localCoord) {
		Map<LocalCoord, NBTCompound> map = halfnbt.get(chunkCoord);
		if (map != null) {
			return map.get(localCoord);
		}
		return null;
	}

	public void setCachedTileNBT(ChunkCoord chunkCoord, LocalCoord localCoord, NBTCompound legacyTileData) {
		if (legacyTileData == null) {
			halfnbt.computeIfPresent(chunkCoord, (key, map) -> {
				map.remove(localCoord);
				return map;
			});
		} else {
			halfnbt.computeIfAbsent(chunkCoord, k -> new HashMap<>()).put(localCoord, legacyTileData);
		}
	}

	public void clearCache(ChunkCoord chunk) {
		tiledata.remove(chunk);
		halfnbt.remove(chunk);
	}

}
