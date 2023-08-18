package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;
import java.util.UUID;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.ChatSession;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.utils.reflection.ReflectionUtils;

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
		protected final ProfileProperty[] properties;
		protected String displayNameJson;
		protected Integer ping;
		protected GameMode gamemode;
		protected ChatSession chatData;
		protected Boolean display;

		public PlayerListEntryData(String name, ProfileProperty[] properties, Boolean display, ChatSession chatData, Integer ping, GameMode gamemode, String displayNameJson) {
			this.name = name;
			this.properties = properties;
			this.display = display;
			this.displayNameJson = displayNameJson;
			this.chatData = chatData;
			this.ping = ping;
			this.gamemode = gamemode;
		}

		public String getUserName() {
			return name;
		}

		public ProfileProperty[] getProperties() {
			return properties;
		}

		public ChatSession getChatSession() {
			return chatData;
		}

		public Boolean getDisplay() {
			return display != null ? display : Boolean.TRUE;
		}

		public String getDisplayNameJson() {
			return displayNameJson;
		}

		public int getPing() {
			return ping != null ? ping : 0;
		}

		public GameMode getGameMode() {
			return gamemode != null ? gamemode : GameMode.NOT_SET;
		}

		public String getLegacyDisplayName() {
			return displayNameJson != null ? ChatAPI.fromJSON(displayNameJson, true).toLegacyText() : name;
		}

		public void setChatData(ChatSession chatData) {
			this.chatData = chatData;
		}

		public void setDisplay(Boolean display) {
			this.display = display;
		}

		public void setDisplayNameJson(String displayNameJson) {
			this.displayNameJson = displayNameJson;
		}

		public void setGameMode(GameMode gamemode) {
			this.gamemode = gamemode;
		}

		public void setPing(Integer ping) {
			this.ping = ping;
		}

		@Override
		public String toString() {
			return ReflectionUtils.toStringAllFields(this);
		}

		@Override
		public PlayerListEntryData clone() {
			return new PlayerListEntryData(name, properties, display, chatData, ping, gamemode, displayNameJson);
		}

	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
