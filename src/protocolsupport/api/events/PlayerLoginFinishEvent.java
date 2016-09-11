package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.event.HandlerList;

public class PlayerLoginFinishEvent extends PlayerEvent {

	private final UUID uuid;

	public PlayerLoginFinishEvent(InetSocketAddress address, String username, UUID uuid) {
		super(address, username);
		this.uuid = uuid;
	}

	public UUID getUUID() {
		return uuid;
	}

	private String denyLoginMessage;

	public boolean isLoginDenied() {
		return denyLoginMessage != null;
	}

	public String getDenyLoginMessage() {
		return denyLoginMessage;
	}

	public void denyLogin(String message) {
		this.denyLoginMessage = message;
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
