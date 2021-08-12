package protocolsupport.protocol.storage.netcache;

import java.util.ArrayList;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.reflection.ReflectionUtils;

public class NetworkEntityCache {

	protected final Int2ObjectMap<NetworkEntity> entities = new Int2ObjectOpenHashMap<>();
	protected NetworkEntity player;

	public void setSelf(NetworkEntity player) {
		this.player = player;
		addEntity(player);
	}

	public NetworkEntity getSelf() {
		return player;
	}

	public int getSelfId() {
		return player != null ? player.getId() : -1;
	}

	public void addEntity(NetworkEntity entity) {
		entities.put(entity.getId(), entity);
	}

	public NetworkEntity getEntity(int entityId) {
		return entities.get(entityId);
	}

	public ArrayList<NetworkEntity> getEntities(int... entityIds) {
		if (entityIds.length == 0) {
			return new ArrayList<>();
		}
		ArrayList<NetworkEntity> result = new ArrayList<>(entityIds.length);
		for (int entityId : entityIds) {
			NetworkEntity entity = entities.get(entityId);
			if (entity != null) {
				result.add(entity);
			}
		}
		return result;
	}

	public ArrayList<NetworkEntity> removeEntities(int... entityIds) {
		if (entityIds.length == 0) {
			return new ArrayList<>();
		}
		ArrayList<NetworkEntity> result = new ArrayList<>(entityIds.length);
		for (int entityId : entityIds) {
			if (entityId == getSelfId()) {
				continue;
			}
			NetworkEntity entity = entities.remove(entityId);
			if (entity != null) {
				result.add(entity);
			}
		}
		return result;
	}

	public void clearEntities() {
		entities.clear();
		readdSelf();
	}

	private void readdSelf() {
		if (player != null) {
			addEntity(player);
		}
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
