package protocolsupport.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public abstract class ItemStackWriteEvent extends Event {

	private final ItemStack original;
	public ItemStackWriteEvent(ItemStack original) {
		this.original = original.clone();
	}

	public ItemStack getOriginal() {
		return original.clone();
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
