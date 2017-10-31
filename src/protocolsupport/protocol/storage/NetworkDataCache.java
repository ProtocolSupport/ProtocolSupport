package protocolsupport.protocol.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.util.internal.ThreadLocalRandom;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnObject.PreparedItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InfTransactions;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class NetworkDataCache {

	private static final double acceptableError = 0.1;

	private double x;
	private double y;
	private double z;
	private int teleportConfirmId;

	public int tryTeleportConfirm(double x, double y, double z) {
		if (teleportConfirmId == -1) {
			return -1;
		}
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

	public void setTeleportLocation(double x, double y, double z, int teleportConfirmId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportConfirmId = teleportConfirmId;
	}

	private WindowType windowType = WindowType.PLAYER;
	private int windowId = 0;
	private int windowSlots = 46;

	public void setOpenedWindow(WindowType windowType, int windowId, int slots) {
		this.windowType = windowType;
		this.windowId = windowId;
		this.windowSlots = slots;
	}

	public WindowType getOpenedWindow() {
		return this.windowType;
	}
	
	public int getOpenedWindowId() {
		return this.windowId;
	}
	
	public int getOpenedWindowSlots() {
		return this.windowSlots;
	}

	public void closeWindow() {
		this.windowType = WindowType.PLAYER;
		this.windowId = 0;
		this.windowSlots = 46;
	}

	private int actionNumber = 0;

	public int getActionNumber() {
		return actionNumber++;
	}

	public void resetActionNumber() {
		actionNumber = 0;
	}

	private final TIntObjectHashMap<NetworkEntity> watchedEntities = new TIntObjectHashMap<>();
	private final TIntObjectHashMap<PreparedItem> preparedItems = new TIntObjectHashMap<>();
	private final InfTransactions infTransactions = new InfTransactions();
	private int furnaceFuelTime = 0;
	private int furnaceSmeltTime = 200;
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

	public boolean isSelf(int entityId) {
		return (this.getSelfPlayerEntityId() == entityId);
	}

	public NetworkEntity getWatchedSelf() {
		return player;
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

	public void prepareItem(PreparedItem preparedItem) {
		preparedItems.put(preparedItem.getId(), preparedItem);
	}

	public PreparedItem getPreparedItem(int entityId) {
		return preparedItems.get(entityId);
	}

	public void removePreparedItem(int entityId) {
		preparedItems.remove(entityId);
	}
	
	public InfTransactions getInfTransactions() {
		return infTransactions;
	}
	
	public int getFurnaceFuelTime() {
		return furnaceFuelTime;
	}
	
	public int getFurnaceSmeltTime() {
		return furnaceSmeltTime;
	}
	
	public void setFurnaceFuelTime(int furnaceFuelTime) {
		this.furnaceFuelTime = furnaceFuelTime;
	}
	
	public void setFurnaceSmeltTime(int furnaceSmeltTime) {
		this.furnaceSmeltTime = furnaceSmeltTime;
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

		public String getName(String locale) {
			return displayNameJson == null ? name : ChatAPI.fromJSON(displayNameJson).toLegacyText(locale);
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

	protected String locale = I18NData.DEFAULT_LOCALE;

	public void setLocale(String locale) {
		this.locale = locale.toLowerCase();
	}

	public String getLocale() {
		return locale;
	}

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
	
	public int sendChunkSize() {
		return sentChunks.size();
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
	
	ChunkCoord fakeInvCoords = null;
	public Position prepareFakeInventoryCoords() {
		if(!sentChunks.contains(fakeInvCoords)) {
			fakeInvCoords = new ChunkCoord(-1, 31);
			//fakeInvCoords = sentChunks.stream().findFirst().orElseThrow(RuntimeException::new);
		}
		return getFakeInventoryCoords();
	}
	
	public Position getFakeInventoryCoords() {
		if(!sentChunks.contains(fakeInvCoords)) {
			return null;
		}
		return new Position((fakeInvCoords.x << 4), 0, (fakeInvCoords.z << 4));
	}
	
	

	private GameMode gamemode = GameMode.SURVIVAL;
	private boolean canFly = false;
	private boolean isFlying = false;

	public void setGameMode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public GameMode getGameMode() {
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

	private NBTTagCompoundWrapper signTag;

	public void setSignTag(NBTTagCompoundWrapper signTag) { this.signTag = signTag; }

	public NBTTagCompoundWrapper getSignTag() { return signTag; }

	private String title;
	private int visibleOnScreenTicks = 100; // default fadeIn = 20; default stay = 60; default fadeOut = 20;
	private long lastSentTitle;

	public String getTitle() { return title; }

	public void setTitle(String title) { this.title = title; }

	public int getVisibleOnScreenTicks() { return visibleOnScreenTicks; }

	public void setVisibleOnScreenTicks(int visibleOnScreenTicks) { this.visibleOnScreenTicks = visibleOnScreenTicks; }

	public long getLastSentTitle() { return lastSentTitle; }

	public void setLastSentTitle(long lastSentTitle) { this.lastSentTitle = lastSentTitle; }

	private long inventoryLockMillis = 0;
	
	public void lockInventory() {
		inventoryLockMillis = System.currentTimeMillis();
	}
	
	public boolean isInventoryLocked() {
		return System.currentTimeMillis() - inventoryLockMillis < 450;
	}
}
