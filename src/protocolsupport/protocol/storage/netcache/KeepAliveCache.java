package protocolsupport.protocol.storage.netcache;

import io.netty.util.internal.ThreadLocalRandom;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.Utils;

public class KeepAliveCache {

	protected static final int pingSyncLimit = JavaSystemProperty.getValue("ping.sync.limite", 60 * 20, Integer::parseInt);


	protected long serverKeepAliveId = -1;
	protected int clientKeepAliveId = -1;

	private int getNextClientKeepAliveId() {
		clientKeepAliveId++;
		if (clientKeepAliveId < 1) {
			clientKeepAliveId = (int) ((System.currentTimeMillis() % (60 * 1000)) << 4) + ThreadLocalRandom.current().nextInt(16) + 1;
		}
		return clientKeepAliveId;
	}

	public int storeServerKeepAliveId(long serverKeepAliveId) {
		this.serverKeepAliveId = serverKeepAliveId;
		return getNextClientKeepAliveId();
	}

	public long tryConfirmKeepAlive(int clientKeepAliveId) {
		if (this.clientKeepAliveId == clientKeepAliveId) {
			long cServerKeepAliveId = serverKeepAliveId;
			serverKeepAliveId = -1;
			return cServerKeepAliveId;
		} else {
			return -1;
		}
	}


	protected short nextClientSyncPingId = (short) (((System.currentTimeMillis() % 1000) << 4) + ThreadLocalRandom.current().nextInt(16));
	protected Short2IntMap clientserverSyncPingId = new Short2IntOpenHashMap();

	public short storeServerSyncPingId(int serverId) {
		if (clientserverSyncPingId.size() > pingSyncLimit) {
			throw new IllegalStateException("Too many unanswered sync pings");
		}
		short clientSyncPingId = ++nextClientSyncPingId;
		clientserverSyncPingId.put(clientSyncPingId, serverId);
		return clientSyncPingId;
	}

	public int tryConfirmSyncPing(short clientId) {
		return clientserverSyncPingId.remove(clientId);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
