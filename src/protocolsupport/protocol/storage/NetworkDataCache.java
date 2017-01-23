package protocolsupport.protocol.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;
import protocolsupport.protocol.utils.types.WindowType;

public class NetworkDataCache {

	private double x;
	private double y;
	private double z;
	private int teleportConfirmId;

	public boolean isTeleportConfirmNeeded() {
		return teleportConfirmId != -1;
	}

	public int tryTeleportConfirm(double x, double y, double z) {
		if (
			(Double.doubleToLongBits(this.x) == Double.doubleToLongBits(x)) &&
			(Double.doubleToLongBits(this.y) == Double.doubleToLongBits(y)) &&
			(Double.doubleToLongBits(this.z) == Double.doubleToLongBits(z))
		) {
			int r = teleportConfirmId;
			teleportConfirmId = -1;
			return r;
		}
		return -1;
	}

	public boolean tryTeleportConfirm(int teleportId) {
		if (teleportId == teleportConfirmId) {
			teleportConfirmId = -1;
			return true;
		}
		return false;
	}

	public void setTeleportLocation(double x, double y, double z, int teleportConfirmId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportConfirmId = teleportConfirmId;
	}

	private WindowType windowType = WindowType.PLAYER;

	public void setOpenedWindow(WindowType windowType) {
		this.windowType = windowType;
	}

	public WindowType getOpenedWindow() {
		return this.windowType;
	}

	public void closeWindow() {
		this.windowType = WindowType.PLAYER;
	}


	private final TIntObjectHashMap<WatchedEntity> watchedEntities = new TIntObjectHashMap<>();
	private final TIntObjectHashMap<Byte> watchedEntitiesBaseFlags = new TIntObjectHashMap<>();
	private WatchedPlayer player;
	private final HashMap<UUID, NetworkDataCache.PlayerListEntry> playerlist = new HashMap<>();
	private int dimensionId;
	private float maxHealth = 20.0F;

	public void addWatchedEntity(WatchedEntity entity) {
		watchedEntities.put(entity.getId(), entity);
	}

	public void addWatchedSelfPlayer(WatchedPlayer player) {
		this.player = player;
		addWatchedEntity(player);
	}

	public void addWatchedEntityBaseMeta(int entityId, byte flags) {
		watchedEntitiesBaseFlags.put(entityId, flags);
	}

	public int getSelfPlayerEntityId() {
		return player != null ? player.getId() : -1;
	}

	private void readdSelfPlayer() {
		if (player != null) {
			addWatchedEntity(player);
		}
	}

	public WatchedEntity getWatchedEntity(int entityId) {
		return watchedEntities.get(entityId);
	}

	public byte getWatchedEntityBaseMeta(int entityId) {
		Byte ret = watchedEntitiesBaseFlags.get(entityId);
		return ret == null ? 0 : ret;
	}

	public void removeWatchedEntities(int[] entityIds) {
		for (int entityId : entityIds) {
			watchedEntities.remove(entityId);
			watchedEntitiesBaseFlags.remove(entityId);
		}
		readdSelfPlayer();
	}

	public void clearWatchedEntities() {
		watchedEntities.clear();
		readdSelfPlayer();
	}

	public void addPlayerListEntry(UUID uuid, PlayerListEntry entry) {
		playerlist.put(uuid, entry);
	}

	public PlayerListEntry getPlayerListEntry(UUID uuid) {
		return playerlist.get(uuid);
	}

	public void removePlayerListEntry(UUID uuid) {
		playerlist.remove(uuid);
	}

	public void setDimensionId(int dimensionId) {
		this.dimensionId = dimensionId;
	}

	public boolean hasSkyLightInCurrentDimension() {
		return dimensionId == 0;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public static class PropertiesStorage {
		private final HashMap<String, ProfileProperty> signed = new HashMap<>();
		private final HashMap<String, ProfileProperty> unsigned = new HashMap<>();

		public void add(ProfileProperty property) {
			if (property.hasSignature()) {
				signed.put(property.getName(), property);
			} else {
				unsigned.put(property.getName(), property);
			}
		}

		public List<ProfileProperty> getAll(boolean signedOnly) {
			if (signedOnly) {
				return new ArrayList<>(signed.values());
			} else {
				ArrayList<ProfileProperty> properties = new ArrayList<>();
				properties.addAll(signed.values());
				properties.addAll(unsigned.values());
				return properties;
			}
		}
	}

	public static class PlayerListEntry implements Cloneable {
		private final String name;
		private String displayNameJson;
		private final PropertiesStorage propstorage = new PropertiesStorage();

		public PlayerListEntry(String name) {
			this.name = name;
		}

		public void setDisplayNameJson(String displayNameJson) {
			this.displayNameJson = displayNameJson;
		}

		public PropertiesStorage getProperties() {
			return propstorage;
		}

		public String getUserName() {
			return name;
		}

		public String getName() {
			return displayNameJson == null ? name : ChatAPI.fromJSON(displayNameJson).toLegacyText();
		}

		@Override
		public PlayerListEntry clone() {
			PlayerListEntry clone = new PlayerListEntry(name);
			clone.displayNameJson = displayNameJson;
			for (ProfileProperty property : propstorage.getAll(false)) {
				clone.propstorage.add(property);
			}
			return clone;
		}
	}

}
