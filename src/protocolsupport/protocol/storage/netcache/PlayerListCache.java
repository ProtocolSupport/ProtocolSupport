package protocolsupport.protocol.storage.netcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.utils.Utils;

public class PlayerListCache {

	protected final HashMap<UUID, PlayerListEntry> playerlist = new HashMap<>();

	public void addEntry(UUID uuid, PlayerListEntry entry) {
		playerlist.put(uuid, entry);
	}

	public PlayerListEntry getEntry(UUID uuid) {
		return playerlist.get(uuid);
	}

	public PlayerListEntry removeEntry(UUID uuid) {
		return playerlist.remove(uuid);
	}

	public static class PlayerListEntry {
		protected final String name;
		protected String displayNameJson;
		protected int ping;
		protected GameMode gamemode;
		protected final Map<String, ProfileProperty> propSigned = new HashMap<>();
		protected final Map<String, ProfileProperty> propUnsigned = new HashMap<>();

		public PlayerListEntry(String name, int ping, GameMode gamemode, String displayNameJson, Collection<ProfileProperty> properties) {
			this.name = name;
			this.ping = ping;
			this.gamemode = gamemode;
			this.displayNameJson = displayNameJson;
			properties.forEach(this::addProperty);
		}

		public String getUserName() {
			return name;
		}

		public String getDisplayNameJson() {
			return displayNameJson;
		}

		public void setDisplayNameJson(String displayNameJson) {
			this.displayNameJson = displayNameJson;
		}

		public int getPing() {
			return ping;
		}

		public void setPing(int ping) {
			this.ping = ping;
		}

		public GameMode getGameMode() {
			return gamemode;
		}

		public void setGameMode(GameMode gamemode) {
			this.gamemode = gamemode;
		}

		public String getCurrentName(String locale) {
			return displayNameJson != null ? ChatAPI.fromJSON(displayNameJson).toLegacyText(locale) : name;
		}

		public void addProperty(ProfileProperty property) {
			if (property.hasSignature()) {
				propSigned.put(property.getName(), property);
			} else {
				propUnsigned.put(property.getName(), property);
			}
		}

		public List<ProfileProperty> getProperties(boolean signedOnly) {
			if (signedOnly) {
				return new ArrayList<>(propSigned.values());
			} else {
				ArrayList<ProfileProperty> properties = new ArrayList<>();
				properties.addAll(propSigned.values());
				properties.addAll(propUnsigned.values());
				return properties;
			}
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

		@Override
		public PlayerListEntry clone() {
			return new PlayerListEntry(name, ping, gamemode, displayNameJson, getProperties(false));
		}

	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
