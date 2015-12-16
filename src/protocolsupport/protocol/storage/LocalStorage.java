package protocolsupport.protocol.storage;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;

import com.mojang.authlib.properties.Property;

public class LocalStorage {

	private TIntObjectHashMap<WatchedEntity> watchedEntities = new TIntObjectHashMap<WatchedEntity>();
	private WatchedPlayer player;
	private HashMap<UUID, String> playersNames = new HashMap<UUID, String>();
	@SuppressWarnings("serial")
	private HashMap<UUID, PropertiesStorage> properties = new HashMap<UUID, PropertiesStorage>() {
		@Override
		public PropertiesStorage get(Object uuid) {
			if (!super.containsKey(uuid)) {
				super.put((UUID) uuid, new PropertiesStorage());
			}
			return super.get(uuid);
		}
	};

	public void addWatchedEntity(WatchedEntity entity) {
		watchedEntities.put(entity.getId(), entity);
	}

	public void addWatchedSelfPlayer(WatchedPlayer player) {
		this.player = player;
		addWatchedEntity(player);
	}

	private void readdSelfPlayer() {
		if (player != null) {
			addWatchedEntity(player);
		}
	}

	public WatchedEntity getWatchedEntity(int entityId) {
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

	public void addPlayerListName(UUID uuid, String name) {
		playersNames.put(uuid, name);
	}

	public String getPlayerListName(UUID uuid) {
		String name = playersNames.get(uuid);
		if (name == null) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				return player.getName();
			}
		}
		if (name == null) {
			return "Unknown";
		}
		return name;
	}

	public void removePlayerListName(UUID uuid) {
		playersNames.remove(uuid);
	}

	public void addPropertyData(UUID uuid, Property property) {
		properties.get(uuid).addProperty(property);
	}

	public List<Property> getPropertyData(UUID uuid, boolean signedOnly) {
		return properties.get(uuid).getProperties(signedOnly);
	}

	public void removePropertyData(UUID uuid) {
		properties.remove(uuid);
	}

	private static class PropertiesStorage {
		private final HashMap<String, Property> signed = new HashMap<String, Property>();
		private final HashMap<String, Property> unsigned = new HashMap<String, Property>();

		public void addProperty(Property property) {
			if (property.hasSignature()) {
				signed.put(property.getName(), property);
			} else {
				unsigned.put(property.getName(), property);
			}
		}

		public List<Property> getProperties(boolean signedOnly) {
			if (signedOnly) {
				return new ArrayList<Property>(signed.values());
			} else {
				ArrayList<Property> properties = new ArrayList<>();
				properties.addAll(signed.values());
				properties.addAll(unsigned.values());
				return properties;
			}
		}
	}

}
