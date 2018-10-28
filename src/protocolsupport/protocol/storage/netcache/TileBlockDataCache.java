package protocolsupport.protocol.storage.netcache;

import org.bukkit.block.data.BlockData;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.utils.types.Position;

public class TileBlockDataCache {

	protected final Object2IntOpenHashMap<Position> tiledata = new Object2IntOpenHashMap<>();

	public BlockData getTileBlockData(Position position) {
		return MaterialAPI.getBlockDataByNetworkId(tiledata.getInt(position));
	}

	public void setTileBlockstate(Position position, int blockstate) {
		if (blockstate == 0) {
			tiledata.removeInt(position);
		} else {
			tiledata.put(position, blockstate);
		}
	}

	public void clearCache(int chunkX, int chunkZ) {
		tiledata.keySet().removeIf(pos -> (pos.getX() >> 4) == chunkX && (pos.getZ() >> 4) == chunkZ);
	}

}
