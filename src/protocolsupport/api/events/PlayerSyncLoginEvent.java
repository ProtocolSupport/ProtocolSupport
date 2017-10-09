package protocolsupport.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;

import protocolsupport.api.Connection;

/**
 * This event is fired before the {@link PlayerLoginEvent}
 */
public class PlayerSyncLoginEvent extends PlayerAbstractLoginEvent {

	private final Player player;
	public PlayerSyncLoginEvent(Connection connection, Player player) {
		super(connection, player.getName(), false);
		this.player = player;
	}

	private static final HandlerList list = new HandlerList();

	/**
	 * Returns player associated with this event
	 * @return player associated with this event
	 */
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
