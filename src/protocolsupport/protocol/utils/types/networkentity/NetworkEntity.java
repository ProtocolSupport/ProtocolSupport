package protocolsupport.protocol.utils.types.networkentity;

import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;

import java.util.UUID;

public class NetworkEntity {

	public static NetworkEntity createMob(UUID uuid, int id, int typeId) {
		return new NetworkEntity(uuid, id, NetworkEntityType.getMobByNetworkTypeId(typeId));
	}

	public static NetworkEntity createObject(UUID uuid, int id, int typeId, int objectData) {
		return new NetworkEntity(uuid, id, NetworkEntityType.getObjectByNetworkTypeIdAndData(typeId, objectData));
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
	protected final NetworkEntityDataCache cache;
	protected final Position relRemainderCache;

	public NetworkEntity(UUID uuid, int id, NetworkEntityType type) {
		this.uuid = uuid;
		this.id = id;
		this.type = type;
		this.cache = NetworkEntityDataCacheFactory.create(getType());
		this.relRemainderCache = new Position(0, 0, 0);
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

	public Position getRelRemainderPosition() {
		return relRemainderCache;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
