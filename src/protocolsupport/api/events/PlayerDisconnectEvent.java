package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.HandlerList;

public class PlayerDisconnectEvent extends PlayerEvent {

	public PlayerDisconnectEvent(InetSocketAddress address, String username) {
		super(address, username);
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
