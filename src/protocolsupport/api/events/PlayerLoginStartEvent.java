package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.event.HandlerList;

public class PlayerLoginStartEvent extends PlayerEvent {

	private final String hostname;
	private boolean onlinemode;
	private boolean useonlinemodeuuid;
	private UUID uuid;
	private String denyLoginMessage;

	public PlayerLoginStartEvent(InetSocketAddress address, String username, boolean onlinemode, boolean useonlinemodeuuid, String hostname) {
		super(address, username);
		this.onlinemode = onlinemode;
		this.useonlinemodeuuid = useonlinemodeuuid;
		this.hostname = hostname;
	}

	public String getHostname() {
		return hostname;
	}

	public boolean isOnlineMode() {
		return onlinemode;
	}

	public void setOnlineMode(boolean onlinemode) {
		this.onlinemode = onlinemode;
	}

	public boolean useOnlineModeUUID() {
		return useonlinemodeuuid;
	}

	public void setUseOnlineModeUUID(boolean useonlinemodeuuid) {
		this.useonlinemodeuuid = useonlinemodeuuid;
	}

	public boolean hasForcedUUID() {
		return uuid != null;
	}

	public void setForcedUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getForcedUUID() {
		return uuid;
	}


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
