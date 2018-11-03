package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.PositionMap;

public class TileDataCache {

	protected final PositionMap<Integer> tileblockdatas = new PositionMap.PositionIntMap();
	public PositionMap<Integer> getTileBlockDatas;

	public void removeChunk(ChunkCoord chunk) {
		tileblockdatas.removeChunk(chunk);
	}

}