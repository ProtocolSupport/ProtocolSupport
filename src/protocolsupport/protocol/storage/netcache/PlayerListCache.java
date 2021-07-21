package protocolsupport.protocol.storage.netcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.utils.ReflectionUtils;

public class PlayerListCache {

	protected final HashMap<UUID, PlayerListEntryData> playerlist = new HashMap<>();

	public PlayerListEntryData add(UUID uuid, PlayerListEntryData entry) {
		return playerlist.put(uuid, entry);
	}

	public PlayerListEntryData get(UUID uuid) {
		return playerlist.get(uuid);
	}

	public PlayerListEntryData remove(UUID uuid) {
		return playerlist.remove(uuid);
	}

	public static class PlayerListEntryData {

		protected final String name;
		protected String displayNameJson;
		protected int ping;
		protected GameMode gamemode;

		protected final ArrayList<ProfileProperty> properties = new ArrayList<>();

		public PlayerListEntryData(String name, int ping, GameMode gamemode, String displayNameJson, Collection<ProfileProperty> properties) {
			this.name = name;
			this.ping = ping;
			this.gamemode = gamemode;
			this.displayNameJson = displayNameJson;
			this.properties.addAll(properties);
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
			return displayNameJson != null ? ChatAPI.fromJSON(displayNameJson, true).toLegacyText(locale) : name;
		}

		public void addProperty(ProfileProperty property) {
			properties.add(property);
		}

		public ArrayList<ProfileProperty> getProperties() {
			return properties;
		}

		@Override
		public String toString() {
			return ReflectionUtils.toStringAllFields(this);
		}

		@Override
		public PlayerListEntryData clone() {
			return new PlayerListEntryData(name, ping, gamemode, displayNameJson, properties);
		}

	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
