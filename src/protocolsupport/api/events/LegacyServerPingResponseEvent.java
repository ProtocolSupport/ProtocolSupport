package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LegacyServerPingResponseEvent extends Event {

	private InetSocketAddress address;
	private String motd;
	private int maxPlayers;
	public LegacyServerPingResponseEvent(InetSocketAddress address, String motd, int maxPlayers) {
		this.address = address;
		this.motd = motd;
		this.maxPlayers = maxPlayers;
	}

	public InetSocketAddress getAddress() {
		return address;
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


	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
