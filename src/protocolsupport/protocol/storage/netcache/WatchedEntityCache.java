package protocolsupport.protocol.storage.netcache;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.Utils;

public class WatchedEntityCache {

	protected final TIntObjectHashMap<NetworkEntity> watchedEntities = new TIntObjectHashMap<>();
	protected NetworkEntity player;

	public void addWatchedEntity(NetworkEntity entity) {
		watchedEntities.put(entity.getId(), entity);
	}

	public void addWatchedSelfPlayer(NetworkEntity player) {
		this.player = player;
		addWatchedEntity(player);
	}

	public NetworkEntity getSelfPlayer() {
		return player;
	}

	public int getSelfPlayerEntityId() {
		return player != null ? player.getId() : -1;
	}

	public boolean isSelf(int eId) {
		return getSelfPlayerEntityId() == eId;
	}

	private void readdSelfPlayer() {
		if (player != null) {
			addWatchedEntity(player);
		}
	}

	public NetworkEntity getWatchedEntity(int entityId) {
		return watchedEntities.get(entityId);
	}

	public void removeWatchedEntities(int[] entityIds) {
		for (int entityId : entityIds) {
			watchedEntities.remove(entityId);
		}
		readdSelfPlayer();
	}

	public void clearWatchedEntities() {
		watchedEntities.clear();
		readdSelfPlayer();
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
