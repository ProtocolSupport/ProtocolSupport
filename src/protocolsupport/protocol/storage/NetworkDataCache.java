package protocolsupport.protocol.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.Utils;

public class NetworkDataCache {

	private static final double acceptableError = 0.001;

	private double x;
	private double y;
	private double z;
	private int teleportConfirmId;

	public boolean isTeleportConfirmNeeded() {
		return teleportConfirmId != -1;
	}

	public int tryTeleportConfirm(double x, double y, double z) {
		if (
			(Math.abs(this.x - x) < acceptableError) &&
			(Math.abs(this.y - y) < acceptableError) &&
			(Math.abs(this.z - z) < acceptableError)
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


	private final TIntObjectHashMap<NetworkEntity> watchedEntities = new TIntObjectHashMap<>();
	private NetworkEntity player;
	private final HashMap<UUID, NetworkDataCache.PlayerListEntry> playerlist = new HashMap<>();
	private Environment dimensionId;
	private float maxHealth = 20.0F;

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
		sentChunks.clear();
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

	public void setDimensionId(Environment dimensionId) {
		this.dimensionId = dimensionId;
	}

	public boolean hasSkyLightInCurrentDimension() {
		return dimensionId == Environment.OVERWORLD;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
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

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
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

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

	private final HashSet<ChunkCoord> sentChunks = new HashSet<>();

	public void markSentChunk(int x, int z) {
		sentChunks.add(new ChunkCoord(x, z));
	}

	public void unmarkSentChunk(int x, int z) {
		sentChunks.remove(new ChunkCoord(x, z));
	}

	public boolean isChunkMarkedAsSent(int x, int z) {
		return sentChunks.contains(new ChunkCoord(x, z));
	}

	protected static class ChunkCoord {
		private final int x;
		private final int z;
		public ChunkCoord(int x, int z) {
			this.x = x;
			this.z = z;
		}
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ChunkCoord)) {
				return false;
			}
			ChunkCoord other = (ChunkCoord) obj;
			return (x == other.x) && (z == other.z);
		}
		@Override
		public int hashCode() {
			return (x * 31) + z;
		}
	}

	private int gamemode = 0;
	private boolean canFly = false;
	private boolean isFlying = false;

	public void setGameMode(int gamemode) {
		this.gamemode = gamemode;
	}

	public int getGameMode() {
		return this.gamemode;
	}

	public void updateFlying(boolean canFly, boolean isFlying) {
		this.canFly = canFly;
		this.isFlying = isFlying;
	}

	public boolean canFly() {
		return this.canFly;
	}

	public boolean isFlying() {
		return this.isFlying;
	}

}
