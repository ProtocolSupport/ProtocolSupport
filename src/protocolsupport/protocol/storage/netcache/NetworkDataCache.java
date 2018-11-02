package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.utils.Utils;

public class NetworkDataCache {

	public NetworkDataCache(ConnectionImpl connection) {
		tilecache = new TileDataCache(connection);
	}

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

	protected final TileDataCache tilecache;
	public TileDataCache getTileCache() {
		return tilecache;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
