package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import protocolsupport.api.ProtocolVersion;

public class ServerPingResponseEvent extends Event {

	private InetSocketAddress address;
	private ProtocolInfo info;
	private String motd;
	private String icon;
	private int maxPlayers;
	private List<String> players;
	public ServerPingResponseEvent(InetSocketAddress address, ProtocolInfo info, String icon, String motd, int maxPlayers, List<String> players) {
		this.address = address;
		this.info = info;
		this.icon = icon;
		this.motd = motd;
		this.maxPlayers = maxPlayers;
		this.players = new ArrayList<String>(players);
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	public ProtocolInfo getProtocolInfo() {
		return info;
	}

	public void setProtocolInfo(ProtocolInfo info) {
		this.info = info;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<String> getPlayers() {
		return new ArrayList<String>(players);
	}

	public void setPlayers(List<String> players) {
		if (players == null) {
			this.players = new ArrayList<String>();
		}
		this.players = new ArrayList<String>(players);
	}

	public static class ProtocolInfo {
		private int id;
		private String name;

		public ProtocolInfo(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public ProtocolInfo(ProtocolVersion version, String name) {
			this(version.getId(), name);
		}

		public int getId() {
			return id;
		}

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

}
