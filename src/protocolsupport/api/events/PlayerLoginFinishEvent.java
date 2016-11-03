package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

public class PlayerLoginFinishEvent extends PlayerAbstractLoginEvent {

	private final UUID uuid;
	private final boolean onlineMode;

	public PlayerLoginFinishEvent(Connection connection, String username, UUID uuid, boolean onlineMode) {
		super(connection, username);
		this.uuid = uuid;
		this.onlineMode = onlineMode;
	}

	@Deprecated
	public PlayerLoginFinishEvent(InetSocketAddress address, String username, UUID uuid, boolean onlineMode) {
		this(ProtocolSupportAPI.getConnection(address), username, uuid, onlineMode);
	}

	public UUID getUUID() {
		return uuid;
	}

	public boolean isOnlineMode() {
		return onlineMode;
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
