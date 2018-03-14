package protocolsupport.protocol.storage.netcache;

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

	protected final PEChunkMapCache pechunkmapcache = new PEChunkMapCache();
	public PEChunkMapCache getPEChunkMapCache() {
		return pechunkmapcache;
	}

	private final PETitleCache titlecache = new PETitleCache();
	public PETitleCache getTitleCache() {
		return titlecache;
	}

	private final PEItemEntityCache itementitycache = new PEItemEntityCache();
	public PEItemEntityCache getPEItemEntityCache() {
		return itementitycache;
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
