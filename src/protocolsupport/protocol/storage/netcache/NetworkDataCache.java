package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
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

	protected final NetworkEntityCache entitycache = new NetworkEntityCache();
	public NetworkEntityCache getEntityCache() {
		return entitycache;
	}

	protected final PlayerListCache playerlistcache = new PlayerListCache();
	public PlayerListCache getPlayerListCache() {
		return playerlistcache;
	}

	protected final KeepAliveCache keepalivecache = new KeepAliveCache();
	public KeepAliveCache getKeepAliveCache() {
		return keepalivecache;
	}

	protected final ClientCache attrscache = new ClientCache();
	public ClientCache getClientCache() {
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

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
