package protocolsupport.protocol.utils.types;

import java.util.UUID;

import protocolsupport.utils.Utils;

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

	private final UUID uuid;
	private final int id;
	private final NetworkEntityType type;

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

	private final DataCache cache = new DataCache();

	public DataCache getDataCache() {
		return cache;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public static class DataCache {

		private byte pcBaseFlags = 0;
		private boolean firstMeta = true;

		public byte getPcBaseFlags() {
			return pcBaseFlags;
		}

		public boolean getPcBaseFlag(int bitpos) {
			return (pcBaseFlags & (1 << (bitpos - 1))) != 0;
		}

		public void setPcBaseFlag(int bitpos, boolean value) {
			setPcBaseFlag(bitpos, value ? 1 : 0);
		}

		public void setPcBaseFlag(int bitpos, int value) {
			pcBaseFlags &= ~(1 << (bitpos - 1));
			pcBaseFlags |= (value << (bitpos - 1));
		}

		public void setPcBaseFlags(byte pcBaseFlags) {
			this.pcBaseFlags = pcBaseFlags;
		}

		public boolean isFirstMeta() {
			return firstMeta;
		}

		public void setFirstMeta(boolean firstMeta) {
			this.firstMeta = firstMeta;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
