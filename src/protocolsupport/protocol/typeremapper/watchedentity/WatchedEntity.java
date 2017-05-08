package protocolsupport.protocol.typeremapper.watchedentity;

import java.text.MessageFormat;
import java.util.UUID;

public class WatchedEntity {

	public static WatchedEntity createMob(UUID uuid, int id, int typeId) {
		return new WatchedEntity(uuid, id, WatchedType.getMobByTypeId(typeId));
	}

	public static WatchedEntity createObject(UUID uuid, int id, int typeId, int objectData) {
		return new WatchedEntity(uuid, id, WatchedType.getObjectByTypeAndData(typeId, objectData));
	}

	public static WatchedEntity createPlayer(UUID uuid, int id) {
		return new WatchedEntity(uuid, id, WatchedType.PLAYER);
	}

	public static WatchedEntity createPlayer(int id) {
		return createPlayer(null, id);
	}

	private final UUID uuid;
	private final int id;
	private final WatchedType type;

	public WatchedEntity(UUID uuid, int id, WatchedType type) {
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

	public WatchedType getType() {
		return type;
	}

	private final DataCache cache = new DataCache();

	public DataCache getDataCache() {
		return cache;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}(UUID: {1}, Id: {2}, Type: {3})", getClass().getSimpleName(), getUUID(), getId(), getType());
	}

	public static class DataCache {
		public byte baseMetaFlags;
	}

}
