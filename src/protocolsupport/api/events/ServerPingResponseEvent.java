package protocolsupport.api.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.IconUtils;
import protocolsupport.zplatform.ServerPlatform;

/**
 * This event is fired after client pings the server
 */
public class ServerPingResponseEvent extends ConnectionEvent {

	protected ProtocolInfo info;
	protected String motd;
	protected String icon;
	protected int maxPlayers;
	protected List<String> players;

	public ServerPingResponseEvent(Connection connection, ProtocolInfo info, String icon, String motd, int maxPlayers, List<String> players) {
		super(connection);
		setProtocolInfo(info);
		setIcon(icon);
		setMotd(motd);
		setMaxPlayers(maxPlayers);
		setPlayers(players);
	}

	/**
	 * Returns protocol info
	 * @return protocol info
	 */
	public ProtocolInfo getProtocolInfo() {
		return info;
	}

	/**
	 * Sets protocol info <br>
	 * If protocol info is null default one is used
	 * @param info protocol info
	 */
	public void setProtocolInfo(ProtocolInfo info) {
		this.info = info != null ? info : new ProtocolInfo(-1, "ProtocolSupport");
	}

	/**
	 * Returns icon
	 * @return icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets icon <br>
	 * To construct a valid icon data use {@link IconUtils}
	 * @param icon icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Returns MotD
	 * @return MotD
	 */
	public String getMotd() {
		return motd;
	}

	/**
	 * Sets MotD <br>
	 * If MotD is null default one is used
	 * @param motd motd
	 */
	public void setMotd(String motd) {
		this.motd = motd != null ? motd : "A minecraft server (ProtocolSupport)";
	}

	/**
	 * Returns max players amount
	 * @return max players amount
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Sets max players amount
	 * @param maxPlayers max players amount
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	/**
	 * Returns player list copy
	 * @return player list copy
	 */
	public List<String> getPlayers() {
		return new ArrayList<>(players);
	}

	/**
	 * Sets player list
	 * @param players player list
	 */
	public void setPlayers(List<String> players) {
		this.players = players != null ? new ArrayList<>(players) : new ArrayList<>();
	}

	public static class ProtocolInfo {
		private final int id;
		private final String name;

		public ProtocolInfo(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public ProtocolInfo(ProtocolVersion version, String name) {
			this(version.getId(), name);
		}

		/**
		 * Returns protocol id
		 * @return protocol id
		 */
		public int getId() {
			return id;
		}

		/**
		 * Returns server version info
		 * @return server version info
		 */
		public String getName() {
			return name;
		}
	}

	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

	public static String getServerModName() {
		return ServerPlatform.get().getMiscUtils().getModName();
	}

	public static String getServerVersionName() {
		return ServerPlatform.get().getMiscUtils().getVersionName();
	}

}
