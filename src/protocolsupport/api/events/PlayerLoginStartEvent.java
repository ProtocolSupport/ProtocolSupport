package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

public class PlayerLoginStartEvent extends PlayerAbstractLoginEvent {

	private final String hostname;
	private boolean onlinemode;
	private boolean useonlinemodeuuid;
	private UUID uuid;

	public PlayerLoginStartEvent(Connection connection, String username, boolean onlinemode, boolean useonlinemodeuuid, String hostname) {
		super(connection, username);
		this.onlinemode = onlinemode;
		this.useonlinemodeuuid = useonlinemodeuuid;
		this.hostname = hostname;
	}

	@Deprecated
	public PlayerLoginStartEvent(InetSocketAddress address, String username, boolean onlinemode, boolean useonlinemodeuuid, String hostname) {
		this(ProtocolSupportAPI.getConnection(address), username, onlinemode, useonlinemodeuuid, hostname);
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


	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
