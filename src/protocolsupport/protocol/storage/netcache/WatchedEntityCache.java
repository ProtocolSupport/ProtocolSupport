package protocolsupport.protocol.storage.netcache;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.Utils;

public class WatchedEntityCache {

	protected final Int2ObjectOpenHashMap<NetworkEntity> watchedEntities = new Int2ObjectOpenHashMap<>();
	protected NetworkEntity player;

	public void addWatchedEntity(NetworkEntity entity) {
		watchedEntities.put(entity.getId(), entity);
	}

	public void addWatchedSelfPlayer(NetworkEntity player) {
		this.player = player;
		addWatchedEntity(player);
	}

	public int getSelfPlayerEntityId() {
		return player != null ? player.getId() : -1;
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
