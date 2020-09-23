package protocolsupport.protocol.types.networkentity;

import java.util.Objects;
import java.util.UUID;

import protocolsupport.utils.Utils;

public class NetworkEntity {

	public static NetworkEntity createMob(UUID uuid, int id, NetworkEntityType type) {
		return new NetworkEntity(uuid, id, type);
	}

	public static NetworkEntity createObject(UUID uuid, int id, NetworkEntityType type) {
		return new NetworkEntity(uuid, id, type);
	}

	public static NetworkEntity createGlobal(int id, NetworkEntityType type) {
		return new NetworkEntity(null, id, type);
	}

	public static NetworkEntity createPlayer(UUID uuid, int id) {
		return new NetworkEntity(uuid, id, NetworkEntityType.PLAYER);
	}

	public static NetworkEntity createPlayer(int id) {
		return createPlayer(null, id);
	}

	protected final UUID uuid;
	protected final int id;
	protected final NetworkEntityType type;

	protected final NetworkEntityDataCache cache = new NetworkEntityDataCache();

	public NetworkEntity(UUID uuid, int id, NetworkEntityType type) {
		this.uuid = uuid;
		this.id = id;
		this.type = type;
	}

	public UUID getUUID() {
		return uuid;
	}

	public int getId() {
		return id;
	}

	public NetworkEntityType getType() {
		return type;
	}

	public NetworkEntityDataCache getDataCache() {
		return cache;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NetworkEntity other = (NetworkEntity) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
