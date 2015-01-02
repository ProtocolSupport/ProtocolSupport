package protocolsupport.protocol.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mojang.authlib.properties.Property;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.protocol.watchedentites.WatchedEntity;

public class LocalStorage {

	private TIntObjectHashMap<WatchedEntity> watchedEntities = new TIntObjectHashMap<WatchedEntity>();
	private HashMap<UUID, String> playerlist = new HashMap<UUID, String>();
	@SuppressWarnings("serial")
	private HashMap<UUID, ArrayList<Property>> properties = new HashMap<UUID, ArrayList<Property>>() {
		@Override
		public ArrayList<Property> get(Object uuid) {
			if (!super.containsKey(uuid)) {
				super.put((UUID) uuid, new ArrayList<Property>());
			}
			return super.get(uuid);
		}
	};

	public void addWatchedEntity(WatchedEntity entity) {
		watchedEntities.put(entity.getId(), entity);
	}

	public WatchedEntity getWatchedEntity(int entityId) {
		return watchedEntities.get(entityId);
	}

	public void removeWatchedEntities(int[] entityIds) {
		for (int entityId : entityIds) {
			watchedEntities.remove(entityId);
		}
	}

	public void addPlayerListName(UUID uuid, String name) {
		playerlist.put(uuid, name);
	}

	public String getPlayerListName(UUID uuid) {
		String name = playerlist.get(uuid);
		if (name == null) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				return player.getName();
			}
		}
		return name;
	}

	public void addPropertyData(UUID uuid, Property property) {
		ArrayList<Property> lproperties = properties.get(uuid);
		Iterator<Property> iterator = lproperties.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getName().equals(property.getName())) {
				iterator.remove();
			}
		}
		lproperties.add(property);
	}

	public ArrayList<Property> getPropertyData(UUID uuid, boolean filterNonSigned) {
		ArrayList<Property> lproperties = properties.get(uuid);
		if (!filterNonSigned) {
			return lproperties;
		} else {
			Iterator<Property> iterator = lproperties.iterator();
			while (iterator.hasNext()) {
				if (!iterator.next().hasSignature()) {
					iterator.remove();
				}
			}
			return lproperties;
		}
	}

}
