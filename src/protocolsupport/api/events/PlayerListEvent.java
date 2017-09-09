package protocolsupport.api.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.utils.Utils;

/***
 * This class is a hack.
 * Scheduled to be removed after proper packetAPI is written.
 */
public class PlayerListEvent extends Event {

	Connection connection;
	List<Info> infos;
	
	public PlayerListEvent(Connection connection, List<Info> infos) {
		this.connection = connection;
		this.infos = infos;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public List<Info> getInfos() {
		return infos;
	}
	
	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}
	
	public static class Info {
		private UUID uuid;
		private String username;
		private int ping;
		private GameMode gamemode;
		private String displayNameJson;
		private ProfileProperty[] properties;

		public Info(UUID uuid, String username, int ping, GameMode gamemode, String displayNameJson, ProfileProperty[] properties) {
			this.uuid = uuid;
			this.username = username;
			this.ping = ping;
			this.gamemode = gamemode;
			this.displayNameJson = displayNameJson;
			this.properties = properties;
		}
		
		public String getName(String locale) {
			return displayNameJson == null ? username : ChatAPI.fromJSON(displayNameJson).toLegacyText(locale);
		}

		public UUID getUuid() {
			return uuid;
		}

		public String getUsername() {
			return username;
		}

		public int getPing() {
			return ping;
		}

		public GameMode getGamemode() {
			return gamemode;
		}

		public String getDisplayNameJson() {
			return displayNameJson;
		}

		public ProfileProperty[] getProperties() {
			return properties;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}
	
}
