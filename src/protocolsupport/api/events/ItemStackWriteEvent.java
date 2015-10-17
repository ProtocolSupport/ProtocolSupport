package protocolsupport.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.ProtocolVersion;

public abstract class ItemStackWriteEvent extends Event {

	private final ProtocolVersion version;
	private final ItemStack original;
	public ItemStackWriteEvent(ProtocolVersion version, ItemStack original) {
		this.version = version;
		this.original = original.clone();
	}

	public ItemStack getOriginal() {
		return original.clone();
	}

	public ProtocolVersion getVersion() {
		return version;
	}

	public abstract ItemStack getResult();

	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
