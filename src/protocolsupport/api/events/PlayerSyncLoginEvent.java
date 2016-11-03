package protocolsupport.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

public class PlayerSyncLoginEvent extends PlayerAbstractLoginEvent {

	private final Player player;
	public PlayerSyncLoginEvent(Connection connection, Player player) {
		super(connection, player.getName(), false);
		this.player = player;
	}

	private static final HandlerList list = new HandlerList();

	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
