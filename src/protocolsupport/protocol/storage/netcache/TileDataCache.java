package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.PositionMap;

public class TileDataCache {

	protected final TileNBTRemapper tileremapper;

	public TileDataCache(ConnectionImpl connection) {
		this.tileremapper =  new TileNBTRemapper(connection);
	}

	public TileNBTRemapper getTileRemapper() {
		return tileremapper;
	}

	protected final PositionMap<Integer> tileblockdatas = new PositionMap.PositionIntMap();
	public PositionMap<Integer> getTileBlockDatas;

	public void removeChunk(ChunkCoord chunk) {
		tileblockdatas.removeChunk(chunk);
	}

}