package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.utils.Utils;

public class NetworkDataCache {

	protected final MovementCache movecache = new MovementCache();
	public MovementCache getMovementCache() {
		return movecache;
	}

	protected final WindowCache windowcache = new WindowCache();
	public WindowCache getWindowCache() {
		return windowcache;
	}

	protected final WatchedEntityCache wentitycache = new WatchedEntityCache();
	public WatchedEntityCache getWatchedEntityCache() {
		return wentitycache;
	}

	protected final PlayerListCache playerlistcache = new PlayerListCache();
	public PlayerListCache getPlayerListCache() {
		return playerlistcache;
	}

	protected final KeepAliveCache keepalivecache = new KeepAliveCache();
	public KeepAliveCache getKeepAliveCache() {
		return keepalivecache;
	}

	protected final AttributesCache attrscache = new AttributesCache();
	public AttributesCache getAttributesCache() {
		return attrscache;
	}

	protected final CustomPayloadChannelsCache cpccache = new CustomPayloadChannelsCache();
	public CustomPayloadChannelsCache getChannelsCache() {
		return cpccache;
	}

	protected final ChunkCache chunkcache = new ChunkCache();
	public ChunkCache getChunkCache() {
		return chunkcache;
	}

	protected final PEChunkMapCache pechunkmapcache = new PEChunkMapCache();
	public PEChunkMapCache getPEChunkMapCache() {
		return pechunkmapcache;
	}

	protected final PETitleCache petitlecache = new PETitleCache();
	public PETitleCache getPETitleCache() {
		return petitlecache;
	}

	protected final PETileCache petilecache = new PETileCache();
	public PETileCache getPETileCache() {
		return petilecache;
	}

	private final PEInventoryCache inventorycache = new PEInventoryCache();
	public PEInventoryCache getPEInventoryCache() {
		return inventorycache;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
